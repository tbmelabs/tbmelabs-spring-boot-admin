package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class SignupEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String SIGNUP_ENDPOINT = "/signup";

  private static final String PASSWORD_PARAMETER_NAME = "password";
  private static final String CONFIRMATION_PARAMETER_NAME = "confirmation";

  private User testUser;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository authorityRepository;

  @Rule
  public ExpectedException passwordException = ExpectedException.none();

  @Rule
  public ExpectedException confirmationException = ExpectedException.none();

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();
    authorityRepository.deleteAll();
    userRepository.deleteAll();

    testUser = new User();
    testUser.setUsername("Testuser");
    testUser.setPassword("Password99$");
    testUser.setConfirmation("Password99$");
    testUser.setEmail("some.test@email.ch");

    authorityRepository.save(new Role(SecurityRole.USER));

    BruteforceFilterService.resetFilter();
  }

  @Test
  public void postToSignupEndpointShouldSaveNewUser() throws Exception {
    mockMvc
        .perform(post(SIGNUP_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject(new ObjectMapper().writeValueAsString(testUser))
                .put(PASSWORD_PARAMETER_NAME, testUser.getPassword())
                .put(CONFIRMATION_PARAMETER_NAME, testUser.getConfirmation()).toString()))
        .andDo(print()).andExpect(status().is2xxSuccessful());

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
        .andDo(print()).andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString());

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