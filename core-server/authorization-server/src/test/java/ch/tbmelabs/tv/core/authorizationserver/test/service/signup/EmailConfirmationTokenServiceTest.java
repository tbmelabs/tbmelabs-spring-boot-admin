package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.EmailConfirmationTokenService;

public class EmailConfirmationTokenServiceTest {
  @Mock
  private EmailConfirmationTokenCRUDRepository mockEmailConfirmationTokenRepository;

  @Spy
  @InjectMocks
  private EmailConfirmationTokenService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(null).when(mockEmailConfirmationTokenRepository).findByTokenString(Mockito.anyString());
  }

  @Test
  public void emailConfirmationTokenSerivceShouldBeAnnotated() {
    assertThat(EmailConfirmationTokenService.class).hasAnnotation(Service.class);
  }

  @Test
  public void emailConfirmationTokenServiceShouldHavePublicConstructor() {
    assertThat(new EmailConfirmationTokenService()).isNotNull();
  }

  @Test
  public void createUniqueEmailConfirmationTokenShouldReturnNewUUID() {
    assertThat(UUID.fromString(fixture.createUniqueEmailConfirmationToken())).isNotNull();
  }
}