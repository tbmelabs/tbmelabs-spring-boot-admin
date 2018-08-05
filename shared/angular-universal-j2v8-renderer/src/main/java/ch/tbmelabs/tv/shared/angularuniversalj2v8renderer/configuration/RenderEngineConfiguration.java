package ch.tbmelabs.tv.shared.angularuniversalj2v8renderer.configuration;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Original source:
 * https://github.com/swaechter/angularj-universal/blob/master/angularj-universal-renderer/src/main/java/ch/swaechter/angularjuniversal/renderer/configuration/RenderConfiguration.java
 */
@Data
@AllArgsConstructor
public class RenderEngineConfiguration {
	private final String templateContent;
	private final File serverBundleFile;
	private final int engines;
	private final boolean livereload;
	private final Charset charset;
	private final List<String> routes;
}
