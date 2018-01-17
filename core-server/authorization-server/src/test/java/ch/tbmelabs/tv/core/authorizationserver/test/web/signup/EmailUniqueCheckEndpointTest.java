package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser.CreateTestUser;

@Transactional
@CreateTestUser(username = "Testuser", email = "some.test@email.ch", password = "Password99$", confirmation = "Password99$")
public class EmailUniqueCheckEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String EMAIL_UNIQUE_CHECK_ENDPOINT = "/signup/is-email-unique";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String EMAIL_NOT_UNIQUE_ERROR_MESSAGE = "E-mail address already in use!";

  private static final String EXISTING_EMAIL = "some.test@email.ch";
  private static final String VALID_EMAIL = "tbme@localhost.tv";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    BruteforceFilterService.resetFilter();
  }

  @Test(expected = NestedServletException.class)
  public void registrationWithExistingEmailShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(EMAIL_PARAMETER_NAME, EXISTING_EMAIL).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getCause().getMessage()).isEqualTo(EMAIL_NOT_UNIQUE_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void registrationWithNewEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIL).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}