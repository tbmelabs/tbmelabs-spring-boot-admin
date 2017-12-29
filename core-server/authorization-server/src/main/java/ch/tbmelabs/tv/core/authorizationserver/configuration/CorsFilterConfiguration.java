package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfiguration {
  private static final String LOGIN_ENDPOINT_URI = "/signin";
  private static final String LOGOUT_ENDPOINT_URI = "/logout";

  @Bean
  public CorsFilter logoutCorsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();

    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("GET");

    source.registerCorsConfiguration(LOGIN_ENDPOINT_URI, config);
    source.registerCorsConfiguration(LOGOUT_ENDPOINT_URI, config);

    return new CorsFilter(source);
  }
}