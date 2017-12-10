package ch.tbmelabs.tv.core.authorizationserver.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ch.tbmelabs.tv.shared.domain.authentication" })
public class SharedDomainConfig {
}