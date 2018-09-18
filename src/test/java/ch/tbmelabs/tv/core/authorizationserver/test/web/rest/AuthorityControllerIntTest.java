package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class AuthorityControllerIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Value("${spring.data.rest.base-path}/authorities")
  private String authorityEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "AuthorityControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_ADMIN})
  public void getAuthoritiesEndpointIsAccessibleToServerAdmins() throws Exception {
    mockMvc.perform(get(authorityEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "AuthorityControllerIntTestUser",
    authorities = {UserRoleConstants.SERVER_SUPPORT})
  public void getAuthoritiesEndpointIsNotAccessibleToNonServerAdmins() throws Exception {
    mockMvc.perform(get(authorityEndpoint).with(csrf())).andDo(print())
      .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}
