package ch.tbmelabs.tv.core.authorizationserver.test.signup.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class UsernameValidationTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String USERNAME_VALIDATION_ENDPOINT = "/signup/does-username-match-format";
  private static final String USERNAME_PARAMETER_NAME = "username";

  private static final String USERNAME_VALIDATION_ERROR_MESSAGE = "Username does not match format!";

  private static final String VALID_USERNAME = "ThisIsAUsername";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void tooShortUsernameShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(USERNAME_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(USERNAME_PARAMETER_NAME, RandomStringUtils.randomAlphabetic(4)).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(USERNAME_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void tooLongUsernameShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(USERNAME_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(
              new JSONObject().put(USERNAME_PARAMETER_NAME, RandomStringUtils.randomAlphabetic(65)).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(USERNAME_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void usernameWithSpecialCharsShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(USERNAME_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(
              new JSONObject().put(USERNAME_PARAMETER_NAME, RandomStringUtils.randomAlphabetic(5) + "$").toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(USERNAME_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void validUsernameShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(USERNAME_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(USERNAME_PARAMETER_NAME, VALID_USERNAME).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}