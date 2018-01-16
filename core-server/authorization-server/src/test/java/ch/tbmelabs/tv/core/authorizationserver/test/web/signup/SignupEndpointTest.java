package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.springjunithelpers.lifecicle.RequireTestUser;

@Transactional
@RequireTestUser(name = "testuser")
public class SignupEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String SIGNUP_ENDPOINT = "/signup/do-signup";

  private static final String PASSWORD_PARAMETER_NAME = "password";
  private static final String CONFIRMATION_PARAMETER_NAME = "confirmation";

  private static User testUser = new User();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Rule
  public ExpectedException passwordException = ExpectedException.none();

  @Rule
  public ExpectedException confirmationException = ExpectedException.none();

  // @BeforeClass
  // public void initBean() {
  // testUser.setUsername("Testuser");
  // testUser.setPassword("Password99$");
  // testUser.setConfirmation("Password99$");
  // testUser.setEmail("some.test@email.ch");
  //
  // testUser = new TestDataCreator().createTestUser(testUser, true);
  // }

  @Test
  public void postToSignupEndpointShouldSaveNewUser() throws Exception {
    mockMvc
        .perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject(new ObjectMapper().writeValueAsString(testUser))
                .put(PASSWORD_PARAMETER_NAME, testUser.getPassword())
                .put(CONFIRMATION_PARAMETER_NAME, testUser.getConfirmation()).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));

    assertThat(StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList()).size())
        .isEqualTo(1);
  }

  @Test
  public void postToSignupEndpointShouldReturnCreatedUser() throws Exception {
    JSONObject createdJsonUser = new JSONObject(mockMvc
        .perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject(new ObjectMapper().writeValueAsString(testUser))
                .put(PASSWORD_PARAMETER_NAME, testUser.getPassword())
                .put(CONFIRMATION_PARAMETER_NAME, testUser.getConfirmation()).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString());

    Integer id = createdJsonUser.getInt("id");
    assertThat(id).isNotNull();
    assertThat(id).isPositive();

    Long created = createdJsonUser.getLong("created");
    assertThat(created).isNotNull();
    assertThat(created).isPositive();

    Long lastUpdated = createdJsonUser.getLong("lastUpdated");
    assertThat(lastUpdated).isNotNull();
    assertThat(lastUpdated).isPositive();

    String username = createdJsonUser.getString("username");
    assertThat(username).isNotEmpty();
    assertThat(username).isEqualTo(testUser.getUsername());

    String email = createdJsonUser.getString("email");
    assertThat(email).isNotEmpty();
    assertThat(email).isEqualTo(testUser.getEmail());

    passwordException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("password")).isNullOrEmpty();

    confirmationException.expect(JSONException.class);
    assertThat(createdJsonUser.getString("confirmation")).isNullOrEmpty();

    Boolean isEnabled = createdJsonUser.getBoolean("isEnabled");
    assertThat(isEnabled).isNotNull();
    assertThat(isEnabled).isTrue();

    Boolean isBlocked = createdJsonUser.getBoolean("isBlocked");
    assertThat(isBlocked).isNotNull();
    assertThat(isBlocked).isFalse();

    assertThat(createdJsonUser.getJSONArray("grantedAuthorities").length()).isEqualTo(1);
  }
}