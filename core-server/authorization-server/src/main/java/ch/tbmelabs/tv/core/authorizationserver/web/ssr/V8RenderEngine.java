package ch.tbmelabs.tv.core.authorizationserver.web.ssr;

import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

@Component
public class V8RenderEngine {

  private ResourceLoader resourceLoader;

  private NodeJS nodeJs;
  private V8Object renderAdapter;

  public V8RenderEngine(ResourceLoader resourceLoader) throws IOException {
    this.resourceLoader = resourceLoader;

    nodeJs = NodeJS.createNodeJS();
    new MemoryManager(nodeJs.getRuntime());
  }

  @PostConstruct
  public void postConstruct() throws IOException {
    // Register render adapter;
    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      renderAdapter = v8Array.getObject(0);
    }, "registerRenderAdapter");

    // Register callback
    nodeJs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
      String uuid = v8Array.getString(0);
      String html = v8Array.getString(1);
      V8Object error = v8Array.getObject(2);
      if (error == null) {
        // TODO: Return html
        LoggerFactory.getLogger(V8RenderEngine.class)
            .info("Successfully completed render-request with html '{}'", html);
      } else {
        // TODO: Send error response (HTML 404 - not found)
        LoggerFactory.getLogger(V8RenderEngine.class)
            .error("Failed to complete render-request {} with error: {}", uuid, error);
      }
    }, "receiveRenderedPage");

    // Load bundle
    nodeJs.exec(resourceLoader.getResource("server-side-rendering/server.js").getFile());
    nodeJs.handleMessage();

    // Load HTML
    String htmlTemplate;
    try (FileInputStream file =
        new FileInputStream(resourceLoader.getResource("static/index.html").getFile())) {
      htmlTemplate = file.toString();
    } catch (IOException e) {
      throw e;
    }

    // Apply template
    V8Array parameters = new V8Array(nodeJs.getRuntime());
    parameters.push(htmlTemplate);
    renderAdapter.executeVoidFunction("setHtml", parameters);
    parameters.release();
  }

  @PreDestroy
  public void preDestroy() {
    this.nodeJs.getRuntime().shutdownExecutors(true);
  }
}
