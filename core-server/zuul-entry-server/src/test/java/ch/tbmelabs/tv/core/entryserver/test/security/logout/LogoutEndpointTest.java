package ch.tbmelabs.tv.core.entryserver.test.security.logout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class LogoutEndpointTest extends AbstractZuulApplicationContextAware {
  private static final String LOGOUT_ENDPOINT_URI = "/logout";
  private static final String AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL = "http://localhost/logout";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void successfulLogoutShouldRedirectToAuthorizatoinServerLogout() throws Exception {
    String redirectUrl = mockMvc.perform(post(LOGOUT_ENDPOINT_URI)).andDo(print())
        .andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo(AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL)
        .withFailMessage("A successfull logout must forward to the authorization server to truly logout the user.");
  }
}