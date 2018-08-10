package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

@Component
public class AngularUniversalRenderEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(AngularUniversalRenderEngine.class);

  private static final String SERVER_BUNDLE_LOCATION = "classpath:/server-side-rendering/server.js";

  private static Set<RenderRequest> currentlyRenderingRequests = new HashSet<>();

  private NodeJS nodeJs;
  private MemoryManager memoryManager;
  private V8Object renderAdapter;

  public AngularUniversalRenderEngine(ResourceLoader resourceLoader) throws IOException {
    File serverBundle = resourceLoader.getResource(SERVER_BUNDLE_LOCATION).getFile();

    LOGGER.info("Initilize {} with server runtime '{}'", AngularUniversalRenderEngine.class,
        serverBundle.getAbsolutePath());

    nodeJs = initializeNodeRuntime(serverBundle);

    start();
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

    // TODO: An asynchronous task would handle this
    nodeJs.handleMessage();

    return nodeJs;
  }

  public void start() {
    releaseThreadLock();
  }

  public RenderRequest renderUri(String uri) {
    return completeRenderRequest(new RenderRequest(uri));
  }

  // TODO: Run async (queue)
  private RenderRequest completeRenderRequest(RenderRequest renderRequest) {
    LOGGER.debug("Received render request {} for uri '{}'", renderRequest.getUuid(),
        renderRequest.getUri());

    if (!AngularUniversalRenderEngine.currentlyRenderingRequests.add(renderRequest)) {
      throw new IllegalArgumentException(
          "An error occured while queing " + RenderRequest.class + " " + renderRequest.getUuid());
    }

    acquireThreadLock();

    // TODO: Queue up
    V8Array parameters = new V8Array(nodeJs.getRuntime());
    parameters = new V8Array(nodeJs.getRuntime());
    parameters.push(renderRequest.getUuid());
    parameters.push(renderRequest.getUri());
    renderAdapter.executeVoidFunction("renderPage", parameters);
    parameters.release();

    releaseThreadLock();

    // TODO: Run async (thread)
    while (!renderRequest.isDone()) {
      handleRenderRequests();
    }

    return renderRequest;
  }

  private void handleRenderRequests() {
    acquireThreadLock();

    nodeJs.handleMessage();

    releaseThreadLock();
  }

  private void acquireThreadLock() {
    nodeJs.getRuntime().getLocker().acquire();
  }

  private void releaseThreadLock() {
    nodeJs.getRuntime().getLocker().release();
  }

  private void renderRequestCompleted(String uuid, String html) {
    LOGGER.debug("Render request {} completed", uuid);

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Generated html is: {}", html);
    }

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
  public void preDestroy() {
    LOGGER.info("Destroying {}", AngularUniversalRenderEngine.class);

    memoryManager.release();
    nodeJs.release();
  }
}
