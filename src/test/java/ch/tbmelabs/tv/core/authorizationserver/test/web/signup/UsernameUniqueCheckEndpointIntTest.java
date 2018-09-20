package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserDTOTest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class UsernameUniqueCheckEndpointIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String USERNAME_UNIQUE_CHECK_ENDPOINT = "/signup/is-username-unique";
  private static final String USERNAME_PARAMETER_NAME = "username";

  private static final String VALID_USERNAME = "ValidUsername";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  private User testUser;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    testUser = userRepository.save(UserDTOTest.createTestUser());
  }

  @Test
  public void registrationWithExistingUsernameShouldFailValidation() throws Exception {
    mockMvc
      .perform(post(USERNAME_UNIQUE_CHECK_ENDPOINT).with(csrf())
        .contentType(MediaType.APPLICATION_JSON).content(
          new JSONObject().put(USERNAME_PARAMETER_NAME, testUser.getUsername()).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void registrationWithNewUsernameShouldPassValidation() throws Exception {
    mockMvc
      .perform(post(USERNAME_UNIQUE_CHECK_ENDPOINT).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new JSONObject().put(USERNAME_PARAMETER_NAME, VALID_USERNAME).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}
