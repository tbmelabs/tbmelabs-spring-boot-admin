package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

public class AngularUniversalRenderEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(AngularUniversalRenderEngine.class);

  private static Boolean handleMessages = false;
  private static Set<RenderRequest> currentlyRenderingRequests = new HashSet<>();

  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  private volatile NodeJS nodeJs;
  private MemoryManager memoryManager;
  private volatile V8Object renderAdapter;

  public AngularUniversalRenderEngine(File serverBundle,
      ThreadPoolTaskExecutor threadPoolTaskExecutor) {
    LOGGER.info("Initilize {} with server runtime '{}'", AngularUniversalRenderEngine.class,
        serverBundle.getAbsolutePath());

    this.threadPoolTaskExecutor = threadPoolTaskExecutor;

    initializeNodeRuntime(serverBundle);
  }

  private void initializeNodeRuntime(File serverBundle) {
    nodeJs = NodeJS.createNodeJS();
    memoryManager = new MemoryManager(nodeJs.getRuntime());

    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      renderAdapter = v8Array.getObject(0);
    }, "registerRenderAdapter");

    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      try {
        String uuid = v8Array.getString(0);
        String html = v8Array.getString(1);
        V8Object error = v8Array.getObject(2);
        if (error == null) {
          renderRequestCompleted(uuid, html);
        } else {
          renderRequestFailed(uuid, error);
        }
      } catch (Error e) {
        e.printStackTrace();
      }
    }, "receiveRenderedPage");

    nodeJs.exec(serverBundle);

    start();
  }

  private void acquireThreadLock() {
    nodeJs.getRuntime().getLocker().acquire();
  }

  private void releaseThreadLock() {
    nodeJs.getRuntime().getLocker().release();
  }

  public void start() {
    if (AngularUniversalRenderEngine.handleMessages) {
      return;
    }

    LOGGER.info("Starting {}", AngularUniversalRenderEngine.class);

    AngularUniversalRenderEngine.handleMessages = true;

    releaseThreadLock();

    threadPoolTaskExecutor.execute(() -> {
      while (AngularUniversalRenderEngine.handleMessages) {
        acquireThreadLock();
        nodeJs.handleMessage();
        releaseThreadLock();
      }
    });
  }

  @Async("angularUniversalRenderEngineExecutor")
  public RenderRequest renderUri(String uri) {
    LOGGER.info("This is asynchronously rendered!");

    try {
      return completeRenderRequest(new RenderRequest(uri));
    } catch (Error e) {
      e.printStackTrace();
    }
    
    return null;
  }

  private RenderRequest completeRenderRequest(RenderRequest renderRequest) {
    LOGGER.debug("Received render request {} for uri '{}'", renderRequest.getUuid(),
        renderRequest.getUri());

    if (!AngularUniversalRenderEngine.currentlyRenderingRequests.add(renderRequest)) {
      throw new IllegalArgumentException(
          "An error occured while queing " + RenderRequest.class + " " + renderRequest.getUuid());
    }

    acquireThreadLock();
    V8Array parameters = new V8Array(nodeJs.getRuntime());
    parameters = new V8Array(nodeJs.getRuntime());
    parameters.push(renderRequest.getUuid());
    parameters.push(renderRequest.getUri());
    renderAdapter.executeVoidFunction("renderPage", parameters);
    releaseThreadLock();

    parameters.release();

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

  public synchronized void stop() {
    LOGGER.info("Stopping {}", AngularUniversalRenderEngine.class);

    AngularUniversalRenderEngine.handleMessages = false;
  }

  @PreDestroy
  public synchronized void preDestroy() {
    stop();

    memoryManager.release();
    nodeJs.release();
  }
}
