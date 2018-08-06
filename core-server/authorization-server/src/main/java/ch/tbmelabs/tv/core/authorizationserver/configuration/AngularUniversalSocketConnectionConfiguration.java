package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.eclipsesource.v8.NodeJS;

@Configuration
public class AngularUniversalSocketConnectionConfiguration {
	private ResourceLoader resourceLoader;

	public AngularUniversalSocketConnectionConfiguration(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@PostConstruct
	public void postConstruct() throws UnknownHostException, IOException {
		NodeJS node = NodeJS.createNodeJS();

		node.exec(resourceLoader.getResource("classpath:server-side-rendering/server.js").getFile());
		node.handleMessage();

		node.require(resourceLoader.getResource("classpath:server-side-rendering/client.js").getFile());
	}
}
