package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
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

  private final UserProfile testUserProfile = createTestUserProfile();

  public static UserProfile createTestUserProfile() {
    User user = new User();
    user.setUsername(RandomStringUtils.random(11));
    user.setEmail(user.getUsername() + "@tbme.tv");

    UserProfile profile = new UserProfile(user, new ArrayList<>());
    profile.setPassword(RandomStringUtils.random(60));
    profile.setConfirmation(user.getPassword());

    return profile;
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.SERVER_SUPPORT })
  public void postClientEndpointIsAccessibleToServerSupports() throws Exception {
    mockMvc
        .perform(post(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = { UserAuthority.CONTENT_ADMIN })
  public void postClientEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc
        .perform(post(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
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
    testUserProfile
        .setId(userProfileMapper.toUserProfile(userRepository.save(userProfileMapper.toUser(testUserProfile))).getId());

    mockMvc
        .perform(put(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserProfile)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
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
    testUserProfile.setId(new Random().nextLong());

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