package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

public class AngularUniversalRenderEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(AngularUniversalRenderEngine.class);

  private static Set<RenderRequest> currentlyRenderingRequests = new HashSet<>();

  private PessimisticThreadLockingV8TaskScheduler taskScheduler;

  private NodeJS nodeJs;
  private MemoryManager memoryManager;
  private V8Object renderAdapter;

  public AngularUniversalRenderEngine(File serverBundle) {
    LOGGER.info("Initilize {} with server runtime '{}'", AngularUniversalRenderEngine.class,
        serverBundle.getAbsolutePath());

    nodeJs = initializeNodeRuntime(serverBundle);

    taskScheduler = new PessimisticThreadLockingV8TaskScheduler(nodeJs, true);
  }

  private NodeJS initializeNodeRuntime(File serverBundle) {
    NodeJS nodeJs = NodeJS.createNodeJS();
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

    return nodeJs;
  }

  public RenderRequest renderUri(String uri) {
    return completeRenderRequest(new RenderRequest(uri));
  }

  private RenderRequest completeRenderRequest(RenderRequest renderRequest) {
    LOGGER.info("Received render request {} for uri '{}'", renderRequest.getUuid(),
        renderRequest.getUri());

    if (!AngularUniversalRenderEngine.currentlyRenderingRequests.add(renderRequest)) {
      throw new IllegalArgumentException(
          "An error occured while queing " + RenderRequest.class + " " + renderRequest.getUuid());
    }

    taskScheduler.schedule(() -> {
      LOGGER.info("I am handling a rendering :)");

      V8Array parameters = new V8Array(nodeJs.getRuntime());
      parameters = new V8Array(nodeJs.getRuntime());
      parameters.push(renderRequest.getUuid());
      parameters.push(renderRequest.getUri());
      renderAdapter.executeVoidFunction("renderPage", parameters);
      parameters.release();
    });

    LOGGER.info("Fired scheduled task!");

    return renderRequest;
  }

  private void renderRequestCompleted(String uuid, String html) {
    LOGGER.debug("Render request {} completed", uuid);
    LOGGER.trace("Generated html is: {}", html);

    consumeRenderRequest(uuid, renderRequest -> {
      renderRequest.complete(html);
      removeFromQueue(renderRequest);
    });
  }

  private void renderRequestFailed(String uuid, V8Object error) {
    LOGGER.warn("Render request {} failed with message ''", uuid, error.toString());

    consumeRenderRequest(uuid, (renderRequest) -> {
      renderRequest.completeExceptionally(new IllegalArgumentException(error.toString()));
      removeFromQueue(renderRequest);
    });
  }

  private void consumeRenderRequest(String uuid, Consumer<RenderRequest> consumer) {
    AngularUniversalRenderEngine.currentlyRenderingRequests.stream()
        .filter(renderRequest -> renderRequest.getUuid().equals(uuid)).findFirst()
        .ifPresent(consumer);
  }

  private void removeFromQueue(RenderRequest renderRequest) {
    AngularUniversalRenderEngine.currentlyRenderingRequests.remove(renderRequest);
  }

  @PreDestroy
  public synchronized void preDestroy() {
    LOGGER.info("Destroying {}", AngularUniversalRenderEngine.class);

    taskScheduler.stop();

    memoryManager.release();
    nodeJs.release();
  }
}
