package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationAttemptLogger;

public class AuthenticationAttemptLoggerTest {
  @Mock
  private AuthenticationLogCRUDRepository authenticationLogRepositoryFixture;

  @Mock
  private UserCRUDRepository userRepositoryFixture;

  @Spy
  @InjectMocks
  private AuthenticationAttemptLogger fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(userRepositoryFixture.findByUsername(Mockito.anyString())).thenReturn(new User());
  }

  @Test
  public void authenticationAttemptLoggerShouldBeAnnotated() {
    assertThat(AuthenticationAttemptLogger.class).hasAnnotation(Component.class);
  }

  @Test
  public void authenticationAttemptLoggerShouldSaveNewAttempt() {
    fixture.logAuthenticationAttempt(AUTHENTICATION_STATE.OK, "127.0.0.1", "This is some message.", "Testuser");

    verify(userRepositoryFixture, times(1)).findByUsername(Mockito.anyString());
    verify(authenticationLogRepositoryFixture, times(1)).save(Mockito.any(AuthenticationLog.class));
  }
}