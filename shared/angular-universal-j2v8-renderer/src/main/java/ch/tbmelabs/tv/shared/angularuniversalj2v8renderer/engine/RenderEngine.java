package ch.tbmelabs.tv.shared.angularuniversalj2v8renderer.engine;

import java.net.URI;

/**
 * Original source:
 * https://github.com/swaechter/angularj-universal/blob/master/angularj-universal-renderer/src/main/java/ch/swaechter/angularjuniversal/renderer/engine/RenderEngine.java
 */
public interface RenderEngine {
	String renderPage(URI uri);
}
