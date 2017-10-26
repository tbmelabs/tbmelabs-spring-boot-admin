package ch.tbmelabs.tv.core.authenticationserver.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ch.tbmelabs.tv.shared.resources.authentication" })
public class SharedResourcesConfig {
}