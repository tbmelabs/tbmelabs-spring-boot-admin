package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserProfileTest;

public class EmailUniqueCheckEndpointIntTest
    extends AbstractOAuth2AuthorizationServerContextAwareTest {
  private static final String EMAIL_UNIQUE_CHECK_ENDPOINT = "/signup/is-email-unique";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String EMAIL_NOT_UNIQUE_ERROR_MESSAGE = "E-mail address already in use!";

  private static final String VALID_EMAIL = "valid.email@tbme.tv";

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
    BruteforceFilterService.resetFilter();

    User newUser = UserProfileTest.createTestUser();

    testUser = userRepository.save(newUser);
  }

  @Test
  public void registrationWithExistingEmailShouldFailValidation() throws Exception {
    assertThatThrownBy(() -> mockMvc
        .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, testUser.getEmail()).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
            .isInstanceOf(NestedServletException.class)
            .hasCauseInstanceOf(IllegalArgumentException.class)
            .hasStackTraceContaining(EMAIL_NOT_UNIQUE_ERROR_MESSAGE);
  }

  @Test
  public void registrationWithNewEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIL).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}
