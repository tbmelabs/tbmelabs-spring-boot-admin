package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserProfileMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

public class UserControllerIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Value("${spring.data.rest.base-path}/users")
  private String usersEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserProfileMapper userProfileMapper;

  @Autowired
  private UserCRUDRepository userRepository;

  private UserProfile testUserProfile;

  public static UserProfile createTestUserProfile() {
    UserProfile profile = new UserProfile();
    profile.setUsername(RandomStringUtils.randomAlphabetic(11));
    profile.setEmail(profile.getUsername() + "@tbme.tv");

    return profile;
  }

  @Before
  public void beforeTestSetUp() {
    testUserProfile = createTestUserProfile();

    User existingUser;
    if ((existingUser = userRepository.findOneByUsernameIgnoreCase(testUserProfile.getUsername())
        .orElse(null)) != null) {
      userRepository.delete(existingUser);
    }
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void getClientsEndpointIsAccessibleToServerSupports() throws Exception {
    mockMvc.perform(get(usersEndpoint)).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.CONTENT_ADMIN })
  public void getClientsEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc.perform(get(usersEndpoint)).andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void putClientEndpointIsAccessibleToServerSupports() throws Exception {
    testUserProfile.setPassword("Password$2018");
    testUserProfile.setConfirmation(testUserProfile.getPassword());

    UserProfile persistedUser = userProfileMapper
        .toUserProfile(userRepository.save(userProfileMapper.toUser(testUserProfile)));
    persistedUser.setUsername(RandomStringUtils.randomAlphabetic(11));

    mockMvc
        .perform(put(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(persistedUser)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));

    assertThat(userRepository.findOneByUsernameIgnoreCase(persistedUser.getUsername()).isPresent()).isTrue();
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.CONTENT_ADMIN })
  public void putClientEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc
        .perform(put(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void deleteClientEndpointIsAccessibleToServerSupports() throws Exception {
    testUserProfile.setPassword("Password$2018");
    testUserProfile.setConfirmation(testUserProfile.getPassword());
    testUserProfile
        .setId(userProfileMapper.toUserProfile(userRepository.save(userProfileMapper.toUser(testUserProfile))).getId());

    mockMvc
        .perform(delete(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.CONTENT_ADMIN })
  public void deleteClientEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc
        .perform(delete(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}