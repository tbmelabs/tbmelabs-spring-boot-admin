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

public class EmailValidationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String EMAIL_VALIDATION_ENDPOINT = "/signup/is-email";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String EMAIL_VALIDATION_ERROR_MESSAGE = "Not a valid e-mail address!";

  private static final String INVALID_EMAIL = "ASDFGHJKL";
  private static final String VALID_EMAIl = "tbme@localhost.tv";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void invalidEmailShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(EMAIL_PARAMETER_NAME, INVALID_EMAIL).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(EMAIL_VALIDATION_ERROR_MESSAGE);
  }

  @Test
  public void validEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIl).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}