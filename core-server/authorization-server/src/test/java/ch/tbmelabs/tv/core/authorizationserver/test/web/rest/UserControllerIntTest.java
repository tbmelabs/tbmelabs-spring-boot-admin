package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserDTOTest;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class UserControllerIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Value("${spring.data.rest.base-path}/users")
  private String usersEndpoint;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserCRUDRepository userRepository;

  private UserDTO testUserDTO = createTestUserDTO();

  public static UserDTO createTestUserDTO() {
    User user = UserDTOTest.createTestUser();
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setCreated(user.getCreated());
    dto.setLastUpdated(user.getLastUpdated());
    dto.setUsername(user.getUsername());
    dto.setEmail(user.getEmail());
    dto.setIsEnabled(user.getIsEnabled());
    dto.setIsBlocked(user.getIsBlocked());
    // TODO: Associations
    return dto;
  }

  @Before
  public void beforeTestSetUp() {
    User existingUser;
    if ((existingUser = userRepository.findOneByUsernameIgnoreCase(testUserDTO.getUsername())
        .orElse(null)) != null) {
      userRepository.delete(existingUser);
    }
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser",
      authorities = {UserAuthority.SERVER_SUPPORT})
  public void getClientsEndpointIsAccessibleToServerSupports() throws Exception {
    mockMvc.perform(get(usersEndpoint)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = {UserAuthority.CONTENT_ADMIN})
  public void getClientsEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc.perform(get(usersEndpoint)).andDo(print())
        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser",
      authorities = {UserAuthority.SERVER_SUPPORT})
  public void putClientEndpointIsAccessibleToServerSupports() throws Exception {
    User persistedUser = userRepository.save(UserDTOTest.createTestUser());
    testUserDTO = userMapper.toDto(persistedUser);

    mockMvc
        .perform(put(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(persistedUser)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));

    assertThat(userRepository.findOneByUsernameIgnoreCase(persistedUser.getUsername()).isPresent())
        .isTrue();
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = {UserAuthority.CONTENT_ADMIN})
  public void putClientEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc
        .perform(put(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser",
      authorities = {UserAuthority.SERVER_SUPPORT})
  public void deleteClientEndpointIsAccessibleToServerSupports() throws Exception {
    User persistedUser = userRepository.save(UserDTOTest.createTestUser());
    testUserDTO = userMapper.toDto(persistedUser);

    mockMvc
        .perform(delete(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  @WithMockUser(username = "UserControllerIntTestUser", authorities = {UserAuthority.CONTENT_ADMIN})
  public void deleteClientEndpointIsNotAccessibleToNonServerSupports() throws Exception {
    mockMvc
        .perform(delete(usersEndpoint).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testUserDTO)))
        .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  }
}
