package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.annotation.EnableCentralizedLogging;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCentralizedLogging
@Profile({SpringApplicationProfile.PROD})
public class CentralizedLoggingConfiguration {

}
