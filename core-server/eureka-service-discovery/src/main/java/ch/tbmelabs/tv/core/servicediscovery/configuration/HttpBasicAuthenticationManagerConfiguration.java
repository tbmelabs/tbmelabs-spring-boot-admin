package ch.tbmelabs.tv.core.servicediscovery.configuration;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import ch.tbmelabs.tv.shared.securityutils.authentication.manager.ActuatorEndpointConfiguration;
import ch.tbmelabs.tv.shared.securityutils.authentication.manager.EurekaEndpointConfiguration;
import ch.tbmelabs.tv.shared.securityutils.authentication.manager.provider.AuthenticationProviderFactory;

@Configuration
@ComponentScan(basePackages = {"ch.tbmelabs.tv.shared.securityutils"})
public class HttpBasicAuthenticationManagerConfiguration {

  private EurekaEndpointConfiguration eurekaEndpointConfiguration;
  private ActuatorEndpointConfiguration actuatorEndpointConfiguration;

  public HttpBasicAuthenticationManagerConfiguration(
      EurekaEndpointConfiguration eurekaEndpointConfiguration,
      ActuatorEndpointConfiguration actuatorEndpointConfiguration) {
    this.eurekaEndpointConfiguration = eurekaEndpointConfiguration;
    this.actuatorEndpointConfiguration = actuatorEndpointConfiguration;
  }

  @Bean
  public AuthenticationProvider eurekaEndpointAuthenticationProvider() throws Exception {
    return AuthenticationProviderFactory.buildAuthenticationProvider(
        eurekaEndpointConfiguration.getEurekaEndpointAuthenticationManager());
  }

  @Bean
  public AuthenticationProvider actuatorEndpointAuthenticationProvider() throws Exception {
    return AuthenticationProviderFactory.buildAuthenticationProvider(
        actuatorEndpointConfiguration.getActuatorEndpointAuthenticationManager());
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return new ProviderManager(Arrays.asList(eurekaEndpointAuthenticationProvider(),
        actuatorEndpointAuthenticationProvider()));
  }
}
