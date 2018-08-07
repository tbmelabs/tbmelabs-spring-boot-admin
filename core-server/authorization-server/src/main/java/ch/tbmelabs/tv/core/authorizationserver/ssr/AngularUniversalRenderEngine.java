package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.io.File;
import java.util.Collection;
import javax.annotation.PreDestroy;
import org.springframework.scheduling.annotation.Async;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.RenderRequest;

public class AngularUniversalRenderEngine {
  private static Boolean isShutdownInitialized = false;

  private NodeJS nodeJs;
  private MemoryManager memoryManager;
  private V8Object renderAdapter;

  private Collection<RenderRequest> renderRequestQueue;

  public AngularUniversalRenderEngine(File serverBundle) {
    initializeNodeRuntime(serverBundle);
  }

  private void initializeNodeRuntime(File serverBundle) {
    nodeJs = NodeJS.createNodeJS();
    memoryManager = new MemoryManager(nodeJs.getRuntime());

    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      renderAdapter = v8Array.getObject(0);
    }, "registerRenderAdapter");

    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      String uuid = v8Array.getString(0);
      String html = v8Array.getString(1);
      V8Object error = v8Array.getObject(2);
      if (error == null) {
        completeRenderRequest(uuid, html);
      } else {
        renderRequestFailed(uuid, error);
      }
    }, "receiveRenderedPage");

    nodeJs.exec(serverBundle);
    nodeJs.handleMessage();
  }

  public RenderRequest renderPage(String uid) {
    return new RenderRequest(uid);
  }

  private void completeRenderRequest(String uuid, String html) {
    renderRequestQueue.stream().filter(renderRequest -> renderRequest.getUuid().equals(uuid))
        .findFirst().ifPresent(renderRequest -> {
          renderRequest.complete(html);
          renderRequestQueue.remove(renderRequest);
        });
  }

  private void renderRequestFailed(String uuid, V8Object error) {
    renderRequestQueue.stream().filter(renderRequest -> renderRequest.getUuid().equals(uuid))
        .findFirst().ifPresent(renderRequest -> {
          renderRequest.completeExceptionally(new IllegalArgumentException(error.toString()));
          renderRequestQueue.remove(renderRequest);
        });
  }

  @Async
  private void workOnQueue() {
    while (!AngularUniversalRenderEngine.isShutdownInitialized) {
      if (renderRequestQueue.iterator().hasNext()) {
        completeRenderRequest(renderRequestQueue.iterator().next());
      }

      nodeJs.handleMessage();
    }
  }

  private void completeRenderRequest(RenderRequest renderRequest) {
    // TODO: Is this required?
    nodeJs.handleMessage();

    V8Array parameters = new V8Array(nodeJs.getRuntime());
    parameters = new V8Array(nodeJs.getRuntime());
    parameters.push(renderRequest.getUuid());
    parameters.push(renderRequest.getUri());
    renderAdapter.executeVoidFunction("renderPage", parameters);

    // Release the parameters
    parameters.release();
  }

  @PreDestroy
  private void shutdownNodeRuntime() {
    AngularUniversalRenderEngine.isShutdownInitialized = true;
    memoryManager.release();
    nodeJs.release();
  }
}
