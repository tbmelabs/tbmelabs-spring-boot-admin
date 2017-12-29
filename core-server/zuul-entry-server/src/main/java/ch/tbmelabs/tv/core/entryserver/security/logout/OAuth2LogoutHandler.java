package ch.tbmelabs.tv.core.entryserver.security.logout;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LogoutHandler extends SimpleUrlLogoutSuccessHandler {
  private static final String LOGOUT_ENDPOINT_URI = "/logout";

  @Value("${OAUTH2_SERVER_BASE_URI}")
  private String oauth2ServerUri;

  @PostConstruct
  public void initBean() {
    super.setDefaultTargetUrl(oauth2ServerUri + LOGOUT_ENDPOINT_URI);
  }
}