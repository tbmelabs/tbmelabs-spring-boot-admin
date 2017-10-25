package ch.tbmelabs.tv.authenticationserver.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ch.tbmelabs.tv.resource.authentication" })
public class SharedResourceConfig {
}