package ch.tbmelabs.tv.core.authorizationserver.test.signup.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class PasswordValidationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String PASSWORD_VALIDATION_ENDPOINT = "/signup/does-password-match-format";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String PASSWORD_VALIDATION_ERROR_MESSAGE = "Password does not match format!";

  private static final String TOO_SHORT_PASSWORD = "Asdf$12";
  private static final String PASSWORD_MISSING_NUMBERS = "ASDFghj$";
  private static final String PASSWORD_MISSING_LOWERCASE_LETTERS = "ASDF123$";
  private static final String PASSWORD_MISSING_UPPERCASE_LETTERS = "asdf123$";
  private static final String PASSWORD_MISSING_SPECIAL_CHARS = "ASDfgh123";
  private static final String VALID_PASSWORD = "ASdf99$$";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void tooShortPasswordShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, TOO_SHORT_PASSWORD).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(PASSWORD_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void passwordWithoutNumbersFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_NUMBERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(PASSWORD_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void passwordWithoutLowercaseLettersShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_LOWERCASE_LETTERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(PASSWORD_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void passwordWithoutUppercaseLettersShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_UPPERCASE_LETTERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(PASSWORD_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void passwordWithoutSpecialCharShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_SPECIAL_CHARS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(PASSWORD_VALIDATION_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void validPasswordShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, VALID_PASSWORD).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}