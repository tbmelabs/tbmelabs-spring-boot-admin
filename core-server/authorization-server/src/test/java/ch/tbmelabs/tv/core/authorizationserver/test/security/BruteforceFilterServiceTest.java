package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Transactional
@Profile({ "!" + SpringApplicationProfile.NO_DB })
public class BruteforceFilterServiceTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Before
  public void beforeTestSetUp() {
    ipBlacklistRepository.deleteAll();

    BruteforceFilterService.resetFilter();
  }

  @Test
  public void authenticationFailureListenerShouldRegisterFailingAuthentication() throws Exception {
    mockMvc.perform(
        post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    assertThat(BruteforceFilterService.getState()).hasSize(1)
        .withFailMessage("The %s should catch failed login attempts!", BruteforceFilterService.class);
  }

  @Test
  public void authenticationFailureListenerShouldBanIpAfterMaxLoginAttempts() {
    IntStream.range(0, maxLoginAttempts).forEach(iterator -> {
      try {
        mockMvc.perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid")
            .param(PASSWORD_PARAMETER_NAME, "invalid")).andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    });

    assertThat(ipBlacklistRepository.findAll()).hasSize(1).extracting("ip").containsExactly("127.0.0.1")
        .withFailMessage("It is not possible to ban ip's if the assignment is incorrect!");
  }
}