package ch.tbmelabs.tv.core.authorizationserver.test.service.bruteforce;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class BruteforceFilterServiceTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String IP_ADDRESS = "127.0.0.1";

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  @Autowired
  private BruteforceFilterService bruteforceFilterService;

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Test
  public void bruteforceFilterShouldRememberIpsExceedingMaxLoginAttempts() {
    IntStream.range(0, maxLoginAttempts)
        .forEach(iterator -> bruteforceFilterService.authenticationFromIpFailed(IP_ADDRESS));

    assertThat(ipBlacklistRepository.findAll()).hasSize(1).extracting("ip").containsExactly(IP_ADDRESS);
  }
}