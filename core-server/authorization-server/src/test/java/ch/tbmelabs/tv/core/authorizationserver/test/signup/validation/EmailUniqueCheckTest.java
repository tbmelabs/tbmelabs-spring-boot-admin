package ch.tbmelabs.tv.core.authorizationserver.test.signup.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class EmailUniqueCheckTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String EMAIL_UNIQUE_CHECK_ENDPOINT = "/signup/is-email-unique";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String EMAIL_NOT_UNIQUE_ERROR_MESSAGE = "E-mail address already in use!";

  private static final String VALID_EMAIL = "tbme@localhost.tv";

  private Role testRole;
  private User testUser;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository authorityRepository;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();
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

    BruteforceFilterService.resetFilter();
  }

  @Test
  public void registrationWithExistingEmailShouldFailValidation() throws Exception {
    Exception thrownException = null;

    try {
      mockMvc
          .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(EMAIL_PARAMETER_NAME, testUser.getEmail()).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      thrownException = e;
    }

    assertThat(thrownException).isNotNull();
    assertThat(thrownException.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
    assertThat(thrownException.getCause().getLocalizedMessage()).isEqualTo(EMAIL_NOT_UNIQUE_ERROR_MESSAGE)
        .withFailMessage("Dont overwrite the error message to give any information about existing users to the user!");
  }

  @Test
  public void registrationWithNewEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_UNIQUE_CHECK_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIL).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}