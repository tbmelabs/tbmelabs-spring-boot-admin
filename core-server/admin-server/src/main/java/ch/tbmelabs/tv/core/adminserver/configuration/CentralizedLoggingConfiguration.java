package ch.tbmelabs.tv.core.adminserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.annotation.EnableCentralizedLogging;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@EnableCentralizedLogging
@Profile({SpringApplicationProfile.PROD})
public class CentralizedLoggingConfiguration {

}
