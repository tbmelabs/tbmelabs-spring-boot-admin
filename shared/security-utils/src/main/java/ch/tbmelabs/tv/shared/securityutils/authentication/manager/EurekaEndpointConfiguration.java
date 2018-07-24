package ch.tbmelabs.tv.shared.securityutils.authentication.manager;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ch.tbmelabs.tv.shared.constants.security.ApplicationUserRole;
import ch.tbmelabs.tv.shared.securityutils.configuration.ApplicationProperties;

@Configuration
public class EurekaEndpointConfiguration {
  private ObjectPostProcessor<Object> objectPostProcessor;

  private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  private String eurekaUserName;
  private String eurekaUserPassword;

  public EurekaEndpointConfiguration(ObjectPostProcessor<Object> objectPostProcessor,
      ApplicationProperties applicationProperties) {
    this.objectPostProcessor = objectPostProcessor;

    this.eurekaUserName = applicationProperties.getEureka().getAdministrator().getName();
    this.eurekaUserPassword = applicationProperties.getEureka().getAdministrator().getPassword();
  }

  public AuthenticationManager getEurekaEndpointAuthenticationManager() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.inMemoryAuthentication().passwordEncoder(PASSWORD_ENCODER).withUser(eurekaUserName)
        .password(PASSWORD_ENCODER.encode(eurekaUserPassword))
        .roles(ApplicationUserRole.EUREKA_ROLE);
    return builder.build();
  }
}
