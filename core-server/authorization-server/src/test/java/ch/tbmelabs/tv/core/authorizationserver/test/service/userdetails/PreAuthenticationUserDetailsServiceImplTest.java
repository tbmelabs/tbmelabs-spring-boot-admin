package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.PreAuthenticationUserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;

public class PreAuthenticationUserDetailsServiceImplTest {

  @Mock
  private PreAuthenticatedAuthenticationToken mockToken;

  @Mock
  private OAuth2Authentication mockAuthentication;

  @Mock
  private TokenStore mockTokenStore;

  @Mock
  private UserCRUDRepository mockUserRepository;

  @Spy
  @InjectMocks
  private PreAuthenticationUserDetailsServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(RandomStringUtils.random(11)).when(mockToken).getPrincipal();
    doReturn(RandomStringUtils.random(11)).when(mockAuthentication).getName();

    doReturn(mockAuthentication).when(mockTokenStore)
        .readAuthentication(ArgumentMatchers.anyString());

    doCallRealMethod().when(fixture)
        .loadUserDetails(ArgumentMatchers.any(PreAuthenticatedAuthenticationToken.class));
  }

  @Test
  public void preAuthenticationUserDetailsServiceImplShouldBeAnnotated() {
    assertThat(PreAuthenticationUserDetailsServiceImpl.class).hasAnnotation(Service.class);
  }

  @Test
  public void preAuthenticationUserDetailsServiceImplShouldExtendAuthenticationUserDetailsService() {
    assertThat(AuthenticationUserDetailsService.class)
        .isAssignableFrom(PreAuthenticationUserDetailsServiceImpl.class);
  }

  @Test
  public void preAuthenticationUserDetailsServiceImplShouldHavePublicConstructor() {
    assertThat(new PreAuthenticationUserDetailsServiceImpl()).isNotNull();
  }

  @Test
  public void loadUserDetailsShouldLoadCorrectUserDetailsImpl() {
    doReturn(Optional.of(new User())).when(mockUserRepository)
        .findOneByUsernameIgnoreCase(ArgumentMatchers.anyString());

    assertThat(fixture.loadUserDetails(mockToken)).isInstanceOf(UserDetailsImpl.class)
        .hasFieldOrPropertyWithValue("user",
            mockUserRepository.findOneByUsernameIgnoreCase("").get());
  }

  @Test
  public void loadUserDetailsShouldThrowExceptionIfUserDoesNotExist() {
    doReturn(Optional.ofNullable(null)).when(mockUserRepository)
        .findOneByUsernameIgnoreCase(ArgumentMatchers.anyString());

    assertThatThrownBy(() -> fixture.loadUserDetails(mockToken))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("Username " + mockAuthentication.getName() + " does not exist!");
  }
}
