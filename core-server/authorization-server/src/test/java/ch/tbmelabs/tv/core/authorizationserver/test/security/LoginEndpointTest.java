package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;
import ch.tbmelabs.tv.shared.domain.authentication.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.shared.domain.authentication.user.Role;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

public class LoginEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String LOGIN_PROCESSING_URL = "/";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";
  private static final String BAD_CREDENTIALS_RESPONSE = "Bad credentials";

  private Role testRole;
  private User testUser;

  static {

  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository authorityRepository;

  @Before
  public void beforeTestSetUp() {
    authorityRepository.deleteAll();
    userRepository.deleteAll();

    testRole = new Role();
    testRole.setName("TEST");

    testUser = new User();
    testUser.setUsername("Testuser");
    testUser.setPassword("Password99$");
    testUser.setConfirmation("Password99$");
    testUser.setEmail("some.test@email.ch");

    authorityRepository.save(testRole);
    userRepository.save(testUser);

    UserRoleAssociation grantedAuthority = new UserRoleAssociation();
    grantedAuthority.setUserId(testUser.getId());
    grantedAuthority.setUser(testUser);
    grantedAuthority.setUserRoleId(testRole.getId());
    grantedAuthority.setUserRole(testRole);

    testUser.setGrantedAuthorities(Arrays.asList(grantedAuthority));

    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    String responseMessage = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME,
            testUser.getConfirmation()))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn().getResponse()
        .getErrorMessage();

    assertThat(responseMessage).isEqualTo(BAD_CREDENTIALS_RESPONSE);
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    String responseMessage = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn().getResponse()
        .getErrorMessage();

    assertThat(responseMessage).isEqualTo(BAD_CREDENTIALS_RESPONSE);
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String responseMessage = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, testUser.getConfirmation()))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getContentAsString();

    System.out.println(responseMessage);
  }
}