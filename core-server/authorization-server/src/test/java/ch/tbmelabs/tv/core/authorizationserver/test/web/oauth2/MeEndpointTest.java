package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.TestDataCreator;

@Transactional
@WithMockUser(username = "testUsername", password = "testPassword")
public class MeEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ME_ENDPOINT = "/me";

  private static User testUser = new User();

  @Autowired
  private MockMvc mockMvc;

  @BeforeClass
  public static void beforeClassSetUp() {
    testUser.setUsername("Testuser");
    testUser.setPassword("Password99$");
    testUser.setConfirmation("Password99$");
    testUser.setEmail("some.test@email.ch");

    testUser = new TestDataCreator().createTestUser(testUser, true);
  }

  @Test
  public void meEndpointShouldReturnCorrectAuthenticatoin() throws Exception {
    mockMvc.perform(get(ME_ENDPOINT)).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}