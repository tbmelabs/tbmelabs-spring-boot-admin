package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

public class PrincipalEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ME_ENDPOINT = "/me";
  private static final String USER_ENDPOINT = "/user";
  private static final String PROFILE_ENDPOINT = "/profile";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  private static User testUser;

  public static User createTestUser(String username) {
    User user = new User();
    user.setCreated(new Date());
    user.setLastUpdated(new Date());
    user.setUsername(username);
    user.setEmail(user.getUsername() + "@tbme.tv");
    user.setPassword(RandomStringUtils.random(11));
    user.setIsBlocked(false);
    user.setIsEnabled(true);
    return user;
  }

  @Before
  public void beforeTestSetUp() {
    if (!userRepository.findOneByUsernameIgnoreCase("PrincipalEndpointIntTestUser").isPresent()) {
      testUser = userRepository.save(createTestUser("PrincipalEndpointIntTestUser"));
      testUser.setRoles(testUser.rolesToAssociations(Arrays.asList(roleRepository.save(new Role(UserAuthority.USER)))));
      testUser = userRepository.save(testUser);
    }
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser", authorities = { UserAuthority.USER })
  public void meEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(ME_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser", authorities = { UserAuthority.USER })
  public void userEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(USER_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  private void runJsonCredentialAssertChain(JSONObject jsonCredential) throws JSONException {
    assertThat(jsonCredential.getString("login")).isEqualTo("PrincipalEndpointIntTestUser");
    assertThat(jsonCredential.getString("email")).isEqualTo(testUser.getEmail());
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser", authorities = { UserAuthority.USER })
  public void profileEndpointShouldReturnCorrectUserProfile() throws Exception {
    JSONObject jsonUserRepresentation = new JSONObject(mockMvc.perform(get(PROFILE_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString());

    assertThat(jsonUserRepresentation.getLong("created")).isEqualTo(testUser.getCreated().getTime());
    assertThat(jsonUserRepresentation.getLong("lastUpdated")).isEqualTo(testUser.getLastUpdated().getTime());
    assertThat(jsonUserRepresentation.getLong("id")).isEqualTo(testUser.getId());
    assertThat(jsonUserRepresentation.getString("username")).isEqualTo(testUser.getUsername());
    assertThat(jsonUserRepresentation.getString("email")).isEqualTo(testUser.getEmail());

    assertThatThrownBy(() -> jsonUserRepresentation.getString("password")).isInstanceOf(JSONException.class)
        .hasMessage("No value for password");

    assertThatThrownBy(() -> jsonUserRepresentation.getString("confirmation")).isInstanceOf(JSONException.class)
        .hasMessage("No value for confirmation");

    assertThat(jsonUserRepresentation.getBoolean("isEnabled")).isEqualTo(testUser.getIsEnabled());
    assertThat(jsonUserRepresentation.getBoolean("isBlocked")).isEqualTo(testUser.getIsBlocked());

    assertThat(jsonUserRepresentation.getJSONArray("roles").length()).isEqualTo(1);

    JSONObject actualRole = jsonUserRepresentation.getJSONArray("roles").getJSONObject(0);
    Role expectedRole = testUser.getRoles().stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList())
        .get(0);
    assertThat(actualRole.getLong("created")).isEqualTo(expectedRole.getCreated().getTime());
    assertThat(actualRole.getLong("lastUpdated")).isEqualTo(expectedRole.getLastUpdated().getTime());
    assertThat(actualRole.getLong("id")).isEqualTo(expectedRole.getId());
    assertThat(actualRole.getString("name")).isEqualTo(expectedRole.getName());
    assertThat(actualRole.getString("authority")).isEqualTo(expectedRole.getAuthority());
  }
}