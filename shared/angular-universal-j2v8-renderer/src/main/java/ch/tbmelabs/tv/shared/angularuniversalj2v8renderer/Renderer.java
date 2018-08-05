package ch.tbmelabs.tv.shared.angularuniversalj2v8renderer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import ch.tbmelabs.tv.shared.angularuniversalj2v8renderer.configuration.RenderEngineConfiguration;
import ch.tbmelabs.tv.shared.angularuniversalj2v8renderer.engine.RenderEngine;

/**
 * Original source:
 * https://github.com/swaechter/angularj-universal/blob/master/angularj-universal-renderer/src/main/java/ch/swaechter/angularjuniversal/renderer/Renderer.java
 */
public class Renderer {
	private final List<RenderEngine> renderEngines;
	private final RenderEngineConfiguration renderEngineConfiguration;
	private Date startdate;

	public Renderer(RenderEngineConfiguration renderEngineConfiguration) {
		this.renderEngines = new ArrayList<>();
		this.renderEngineConfiguration = renderEngineConfiguration;
	}

	public synchronized void startRenderer() {
		if (renderEngines.size() != 0) {
			return;
		}

		startdate = new Date();

		for (int i = 0; i < renderEngineConfiguration.getEngines(); i++) {
			RenderEngine renderengine = renderenginefactory.createRenderEngine();
			renderEngines.add(renderengine);

			Thread thread = new Thread(() -> {
				renderengine.startWorking(renderrequests, renderconfiguration);
			});
			thread.start();
		}

		if (renderEngineConfiguration.getLiveReload()) {
			Thread thread = new Thread(() -> {
				while (isRendererRunning()) {
					File file = renderEngineConfiguration.getServerBundleFile();
					if (startdate.before(new Date(file.lastModified()))) {
						stopRenderer();
						startRenderer();
					}
				}
			});
			thread.start();
		}
	}

	public synchronized void stopRenderer() {
		if (renderEngines.size() == 0) {
			return;
		}

		for (int i = 0; i < renderEngineConfiguration.getEngines(); i++) {
			renderrequests.add(Optional.empty());
		}

		while (renderrequests.size() != 0) {
			// Wait for an empty request queue
		}

		renderEngines.clear();
	}

	public synchronized boolean isRendererRunning() {
		return renderEngines.size() != 0;
	}

	public Future<String> addRenderRequest(String uri) {
		RenderRequest renderrequest = new RenderRequest(uri);
		renderrequests.add(Optional.of(renderrequest));
		return renderrequest.getFuture();
	}
}
