package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class PasswordValidationEndpointIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String PASSWORD_VALIDATION_ENDPOINT = "/signup/does-password-match-format";
  private static final String PASSWORD_PARAMETER_NAME = "password";

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
    mockMvc
      .perform(post(PASSWORD_VALIDATION_ENDPOINT).with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, TOO_SHORT_PASSWORD).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void passwordWithoutNumbersFailValidation() throws Exception {
    mockMvc
      .perform(
        post(PASSWORD_VALIDATION_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
          .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_NUMBERS)
            .toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void passwordWithoutLowercaseLettersShouldFailValidation() throws Exception {
    mockMvc
      .perform(
        post(PASSWORD_VALIDATION_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
          .content(new JSONObject()
            .put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_LOWERCASE_LETTERS).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void passwordWithoutUppercaseLettersShouldFailValidation() throws Exception {
    mockMvc
      .perform(
        post(PASSWORD_VALIDATION_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
          .content(new JSONObject()
            .put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_UPPERCASE_LETTERS).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void passwordWithoutSpecialCharShouldFailValidation() throws Exception {
    mockMvc
      .perform(post(PASSWORD_VALIDATION_ENDPOINT).with(csrf())
        .contentType(MediaType.APPLICATION_JSON).content(new JSONObject()
          .put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_SPECIAL_CHARS).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void validPasswordShouldPassValidation() throws Exception {
    mockMvc
      .perform(
        post(PASSWORD_VALIDATION_ENDPOINT).with(csrf()).contentType(MediaType.APPLICATION_JSON)
          .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, VALID_PASSWORD).toString()))
      .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}
