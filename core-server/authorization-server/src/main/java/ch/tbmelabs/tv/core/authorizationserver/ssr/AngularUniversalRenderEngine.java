package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.springframework.scheduling.annotation.Async;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

public class AngularUniversalRenderEngine {
  private static Boolean isShutdownInitialized = true;

  private NodeJS nodeJs;
  private MemoryManager memoryManager;
  private V8Object renderAdapter;

  private static Collection<RenderRequest> currentlyRenderingRequests;

  public AngularUniversalRenderEngine(File serverBundle, Executor executor) {
    initializeNodeRuntime(serverBundle, executor);
  }

  private void initializeNodeRuntime(File serverBundle, Executor executor) {
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
        renderRequestCompleted(uuid, html);
      } else {
        renderRequestFailed(uuid, error);
      }
    }, "receiveRenderedPage");

    nodeJs.exec(serverBundle);

    executor.execute(() -> {
      while (!AngularUniversalRenderEngine.isShutdownInitialized) {
        nodeJs.handleMessage();
      }
    });
  }

  @Async("angularUniversalRenderEngineExecutor")
  public RenderRequest renderUri(String uri) {
    return completeRenderRequest(new RenderRequest(uri));
  }

  private RenderRequest completeRenderRequest(RenderRequest renderRequest) {
    if (!AngularUniversalRenderEngine.currentlyRenderingRequests.add(renderRequest)) {
      throw new IllegalArgumentException(
          "An error occured while queing " + RenderRequest.class + " " + renderRequest.getUuid());
    }

    V8Array parameters = new V8Array(nodeJs.getRuntime());
    parameters = new V8Array(nodeJs.getRuntime());
    parameters.push(renderRequest.getUuid());
    parameters.push(renderRequest.getUri());
    renderAdapter.executeVoidFunction("renderPage", parameters);

    parameters.release();

    return renderRequest;
  }

  private void renderRequestCompleted(String uuid, String html) {
    consumeRenderRequest(uuid, renderRequest -> {
      renderRequest.complete(html);
      removeFromQueue(renderRequest);
    });
  }

  private void renderRequestFailed(String uuid, V8Object error) {
    consumeRenderRequest(uuid, (renderRequest) -> {
      renderRequest.completeExceptionally(new IllegalArgumentException(error.toString()));
      removeFromQueue(renderRequest);
    });
  }

  private void removeFromQueue(RenderRequest renderRequest) {
    AngularUniversalRenderEngine.currentlyRenderingRequests.remove(renderRequest);
  }

  private void consumeRenderRequest(String uuid, Consumer<RenderRequest> consumer) {
    AngularUniversalRenderEngine.currentlyRenderingRequests.stream()
        .filter(renderRequest -> renderRequest.getUuid().equals(uuid)).findFirst()
        .ifPresent(consumer);
  }

  public synchronized void stop() {
    AngularUniversalRenderEngine.isShutdownInitialized = true;
  }

  @PreDestroy
  public synchronized void preDestroy() {
    stop();

    memoryManager.release();
    nodeJs.release();
  }
}
