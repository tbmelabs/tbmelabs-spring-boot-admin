package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = { "ch.tbmelabs.tv.shared.domain.authentication" })
public class SharedDomainConfiguration {
  // TODO: Is this even required? Only reason could be an account manager..
}