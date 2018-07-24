package ch.tbmelabs.tv.shared.securityutils.authentication.manager;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ch.tbmelabs.tv.shared.constants.security.ApplicationUserRole;
import ch.tbmelabs.tv.shared.securityutils.configuration.ApplicationProperties;

@Configuration
public class ActuatorEndpointConfiguration {
  private ObjectPostProcessor<Object> objectPostProcessor;

  private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  private String actuatorUserName;
  private String actuatorUserPassword;

  public ActuatorEndpointConfiguration(ObjectPostProcessor<Object> objectPostProcessor,
      ApplicationProperties applicationProperties) {
    this.objectPostProcessor = objectPostProcessor;

    this.actuatorUserName =
        applicationProperties.getEureka().getInstance().getMetadataMap().getUser().getName();
    this.actuatorUserPassword =
        applicationProperties.getEureka().getInstance().getMetadataMap().getUser().getPassword();
  }


  public AuthenticationManager getActuatorEndpointAuthenticationManager() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.inMemoryAuthentication().passwordEncoder(PASSWORD_ENCODER).withUser(actuatorUserName)
        .password(PASSWORD_ENCODER.encode(actuatorUserPassword))
        .roles(ApplicationUserRole.ACTUATOR_ROLE);
    return builder.build();
  }
}
