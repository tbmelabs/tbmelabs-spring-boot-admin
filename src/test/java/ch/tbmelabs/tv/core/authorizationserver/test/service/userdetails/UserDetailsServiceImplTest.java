package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

public class UserDetailsServiceImplTest {

  @Mock
  private UserCRUDRepository userRepositoryFixture;

  @Spy
  @InjectMocks
  private UserDetailsServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(Optional.of(new User())).when(userRepositoryFixture)
      .findByUsernameIgnoreCase(ArgumentMatchers.anyString());
  }

  @Test
  public void userDetailsServiceImplShouldBeAnnotated() {
    assertThat(UserDetailsServiceImpl.class).hasAnnotation(Service.class);
  }

  @Test
  public void userDetailsServiceImplShouldImplementUserDetailsService() {
    assertThat(UserDetailsService.class).isAssignableFrom(UserDetailsServiceImpl.class);
  }

  @Test
  public void userDetailsServiceImplConstructorShouldAcceptRepository() {
    assertThat(new UserDetailsServiceImpl(userRepositoryFixture)).isNotNull();
  }

  @Test
  public void loadUserByUsernameShouldLoadCorrectUser()
    throws SecurityException {
    UserDetailsImpl userDetails = fixture.loadUserByUsername(RandomStringUtils.random(11));

    assertThat(ReflectionTestUtils.getField(userDetails, "user")).isEqualTo(
      userRepositoryFixture.findByUsernameIgnoreCase(RandomStringUtils.random(11)).get());
  }

  @Test
  public void loadUserShouldThrowExceptionIfUsernameDoesNotExist() {
    doReturn(Optional.empty()).when(userRepositoryFixture)
      .findByUsernameIgnoreCase(ArgumentMatchers.anyString());

    assertThatThrownBy(() -> fixture.loadUserByUsername(RandomStringUtils.random(11)))
      .isInstanceOf(UsernameNotFoundException.class).hasMessageContaining("Username")
      .hasMessageContaining("does not exist!");
  }
}
