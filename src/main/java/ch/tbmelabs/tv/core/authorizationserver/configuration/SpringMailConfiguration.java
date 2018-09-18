package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;

@Configuration
@Profile("!" + SpringApplicationProfileConstants.NO_MAIL)
@PropertySource(value = {"classpath:config/properties/mail.properties"})
public class SpringMailConfiguration {

}
