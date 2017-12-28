package ch.tbmelabs.tv.core.entryserver.security.logout;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
  private static final String LOGOUT_ENDPOINT = "/logout";

  @Value("${OAUTH2_SERVER_BASE_URI}")
  private String authorizationServerBaseUri;

  @PostConstruct
  public void initBean() {
    setDefaultTargetUrl(authorizationServerBaseUri + LOGOUT_ENDPOINT);
  }
}