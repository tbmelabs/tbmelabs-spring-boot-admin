package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

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

public class EmailValidationEndpointIntTest
    extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String EMAIL_VALIDATION_ENDPOINT = "/signup/is-email";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String INVALID_EMAIL = "ASDFGHJKL";
  private static final String VALID_EMAIl = "tbme@localhost.tv";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void invalidEmailShouldFailValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, INVALID_EMAIL).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void validEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIl).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}
