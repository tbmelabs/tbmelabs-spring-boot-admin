package ch.tbmelabs.springbootadmin.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ch.tbmelabs.springbootadmin.test.AbstractSpringBootAdminServerContextAwareTest;

public class RootEndpointOAuth2SSOForwardIntTest extends AbstractSpringBootAdminServerContextAwareTest {

  private static final String FORWARD_HEADER_NAME = "location";
  private static final String EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI = "http://localhost/login";
  private static final String OAUTH2_AUTHENTICATION_ENTRY_POINT_URI =
      "http://localhost/oauth/authorize";

  @Autowired
  private MockMvc mockMvc;

  @Value("${security.oauth2.client.clientId}")
  private String clientId;

  @Test
  public void requestToLoginEndpointShouldForwardToOAuth2AuthorizationEndpoint() throws Exception {
    String loginForward = mockMvc.perform(get("/").with(csrf())).andDo(print())
        .andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse()
        .getHeader(FORWARD_HEADER_NAME);

    assertThat(loginForward).isEqualTo(EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI);

    String ssoForward = mockMvc.perform(get("/login").with(csrf())).andDo(print())
        .andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse()
        .getHeader(FORWARD_HEADER_NAME);

    assertThat(ssoForward).startsWith(OAUTH2_AUTHENTICATION_ENTRY_POINT_URI)
        .contains("client_id=" + clientId)
        .contains("redirect_uri=" + EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI)
        .contains("response_type=code").contains("state=");
  }
}
