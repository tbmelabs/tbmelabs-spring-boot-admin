package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Transactional
public class SignupEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String SIGNUP_ENDPOINT = "/signup/do-signup";
  private static final String PASSWORD_PARAMETER_NAME = "password";
  private static final String CONFIRMATION_PARAMETER_NAME = "confirmation";

  private static final String USER_VALIDATION_ERROR_MESSAGE = "Registration failed. Please check your details!";

  private static User unexistingUser;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private TestUserManager testUserManager;

  @Rule
  public ExpectedException passwordException = ExpectedException.none();

  @Rule
  public ExpectedException confirmationException = ExpectedException.none();

  @BeforeClass
  public static void beforeClassSetUp() {
    unexistingUser = new User();
    unexistingUser.setUsername("SignupUser");
    unexistingUser.setEmail("signup.user@tbme.tv");
    unexistingUser.setPassword("Password99$");
    unexistingUser.setConfirmation(unexistingUser.getPassword());
  }

  @Before
  public void beforeTestSetUp() {
    User existingUser;
    if ((existingUser = userRepository.findByUsername(unexistingUser.getUsername())) != null) {
      userRepository.delete(existingUser);
    }
  }

  @Test(expected = NestedServletException.class)
  public void postToSignupEndpointWithExistingUsernameOrEmailShouldFail() throws Exception {
    try {
      mockMvc
          .perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsString(testUserManager.getUserUser())))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse()
          .getContentAsString();
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(USER_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void postToSignupEndpointShouldSaveNewUser() throws Exception {
    mockMvc.perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
        .content(new JSONObject(new ObjectMapper().writeValueAsString(unexistingUser))
            .put(PASSWORD_PARAMETER_NAME, unexistingUser.getConfirmation())
            .put(CONFIRMATION_PARAMETER_NAME, unexistingUser.getConfirmation()).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));

    assertThat(userRepository.findByUsername(unexistingUser.getUsername())).isNotNull();
  }

  @Test
  public void postToSignupEndpointShouldReturnCreatedUser() throws Exception {
    JSONObject createdJsonUser = new JSONObject(mockMvc
        .perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject(new ObjectMapper().writeValueAsString(unexistingUser))
                .put(PASSWORD_PARAMETER_NAME, unexistingUser.getConfirmation())
                .put(CONFIRMATION_PARAMETER_NAME, unexistingUser.getConfirmation()).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString());

    Integer id = createdJsonUser.getInt("id");
    assertThat(id).isNotNull().isPositive();

    Long created = createdJsonUser.getLong("created");
    assertThat(created).isNotNull().isPositive();

    Long lastUpdated = createdJsonUser.getLong("lastUpdated");
    assertThat(lastUpdated).isNotNull().isPositive();

    String username = createdJsonUser.getString("username");
    assertThat(username).isNotEmpty().isEqualTo(unexistingUser.getUsername());

    String email = createdJsonUser.getString("email");
    assertThat(email).isNotEmpty().isEqualTo(unexistingUser.getEmail());

    passwordException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("password")).isNullOrEmpty();

    confirmationException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("confirmation")).isNullOrEmpty();

    Boolean isEnabled = createdJsonUser.getBoolean("isEnabled");
    assertThat(isEnabled).isNotNull().isTrue();

    Boolean isBlocked = createdJsonUser.getBoolean("isBlocked");
    assertThat(isBlocked).isNotNull().isFalse();

    assertThat(createdJsonUser.getJSONArray("grantedAuthorities").length()).isEqualTo(1);
    assertThat(createdJsonUser.getJSONArray("grantedAuthorities").getString(0)).isEqualTo(SecurityRole.USER);
  }
}