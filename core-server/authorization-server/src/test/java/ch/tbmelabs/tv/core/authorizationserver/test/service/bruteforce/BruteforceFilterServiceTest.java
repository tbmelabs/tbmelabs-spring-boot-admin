package ch.tbmelabs.tv.core.authorizationserver.test.service.bruteforce;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;

public class BruteforceFilterServiceTest {
  private static final Integer MAX_LOGIN_ATTEMPTS = 3;
  private static final String IP_ADDRESS = "127.0.0.1";

  @Mock
  private IPBlacklistCRUDRepository ipBlacklistRepositoryFixture;

  @Spy
  @InjectMocks
  private BruteforceFilterService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "maxLoginAttempts", MAX_LOGIN_ATTEMPTS);
  }

  @Test
  public void bruteforceFilterShouldRememberIpsExceedingMaxLoginAttempts() {
    IntStream.range(0, MAX_LOGIN_ATTEMPTS)
        .forEach(iterator -> fixture.authenticationFromIpFailed(IP_ADDRESS));

    verify(ipBlacklistRepositoryFixture, times(1)).save(Mockito.any(BlacklistedIp.class));
  }
}