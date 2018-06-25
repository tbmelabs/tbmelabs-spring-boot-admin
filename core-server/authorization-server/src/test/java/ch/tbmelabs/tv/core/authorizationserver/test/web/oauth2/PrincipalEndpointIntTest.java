package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.RoleMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import java.util.Collections;
import java.util.HashSet;
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

public class PrincipalEndpointIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String ME_ENDPOINT = "/me";
  private static final String USER_ENDPOINT = "/user";
  private static final String PROFILE_ENDPOINT = "/profile";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleMapper roleMapper;

  private User testUser;

  @Before
  public void beforeTestSetUp() {
    if ((testUser = userRepository.findOneByUsernameIgnoreCase("PrincipalEndpointIntTestUser")
        .orElse(null)) == null) {
      UserDTO newUser = new UserDTO();
      newUser.setUsername("PrincipalEndpointIntTestUser");
      newUser.setEmail(newUser.getUsername() + "@tbme.tv");
      newUser.setPassword(RandomStringUtils.random(11));
      newUser.setRoles(new HashSet<RoleDTO>(Collections.singletonList(
          roleMapper.toDto(roleRepository.save(new Role(RandomStringUtils.random(11)))))));

      testUser = userService.save(newUser);
    }
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser")
  public void meEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(ME_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString()));
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser")
  public void userEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(USER_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString()));
  }

  private void runJsonCredentialAssertChain(JSONObject jsonCredential) throws JSONException {
    assertThat(jsonCredential.getString("login")).isEqualTo(testUser.getUsername());
    assertThat(jsonCredential.getString("email")).isEqualTo(testUser.getEmail());
  }

  @Test
  @WithMockUser(username = "PrincipalEndpointIntTestUser")
  public void profileEndpointShouldReturnCorrectUserDTO() throws Exception {
    JSONObject jsonUserRepresentation = new JSONObject(mockMvc.perform(get(PROFILE_ENDPOINT))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
        .getContentAsString());

    assertThat(jsonUserRepresentation.getLong("created"))
        .isEqualTo(testUser.getCreated().getTime());
    assertThat(jsonUserRepresentation.getLong("lastUpdated"))
        .isEqualTo(testUser.getLastUpdated().getTime());
    assertThat(jsonUserRepresentation.getLong("id")).isEqualTo(testUser.getId());
    assertThat(jsonUserRepresentation.getString("username")).isEqualTo(testUser.getUsername());
    assertThat(jsonUserRepresentation.getString("email")).isEqualTo(testUser.getEmail());

    assertThatThrownBy(() -> jsonUserRepresentation.getString("password"))
        .isInstanceOf(JSONException.class).hasMessage("No value for password");

    assertThatThrownBy(() -> jsonUserRepresentation.getString("confirmation"))
        .isInstanceOf(JSONException.class).hasMessage("No value for confirmation");

    assertThat(jsonUserRepresentation.getBoolean("isEnabled")).isEqualTo(testUser.getIsEnabled());
    assertThat(jsonUserRepresentation.getBoolean("isBlocked")).isEqualTo(testUser.getIsBlocked());

    assertThat(jsonUserRepresentation.getJSONArray("roles").length()).isEqualTo(1);

    JSONObject actualRole = jsonUserRepresentation.getJSONArray("roles").getJSONObject(0);
    Role expectedRole = testUser.getRoles().stream().map(UserRoleAssociation::getUserRole)
        .collect(Collectors.toList()).get(0);
    assertThat(actualRole.getLong("created")).isEqualTo(expectedRole.getCreated().getTime());
    assertThat(actualRole.getLong("lastUpdated"))
        .isEqualTo(expectedRole.getLastUpdated().getTime());
    assertThat(actualRole.getLong("id")).isEqualTo(expectedRole.getId());
    assertThat(actualRole.getString("name")).isEqualTo(expectedRole.getName());
    assertThat(actualRole.getString("authority")).isEqualTo(expectedRole.getAuthority());
  }
}
