package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("!" + SpringApplicationProfile.TEST)
@PropertySource({"classpath:configuration/flyway.properties"})
public class FlywayConfiguration {

}
