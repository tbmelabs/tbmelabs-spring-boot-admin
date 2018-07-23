package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@Profile("!" + SpringApplicationProfile.NO_MAIL)
@PropertySource({"classpath:/config/mail.yml"})
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class SpringMailConfiguration {

}
