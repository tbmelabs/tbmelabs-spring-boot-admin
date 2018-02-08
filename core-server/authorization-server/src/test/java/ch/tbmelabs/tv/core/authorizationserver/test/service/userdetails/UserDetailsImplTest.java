package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;

public class UserDetailsImplTest {
  @Mock
  private User userFixture;

  private static UserDetailsImpl userDetailsImpl;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(userFixture).rolesToAssociations(Mockito.anyList());

    doReturn(RandomStringUtils.randomAlphabetic(11)).when(userFixture).getUsername();
    doReturn(RandomStringUtils.randomAlphabetic(11)).when(userFixture).getEmail();
    doReturn(RandomStringUtils.randomAlphabetic(11)).when(userFixture).getPassword();
    doReturn(true).when(userFixture).getIsEnabled();
    doReturn(false).when(userFixture).getIsBlocked();

    userDetailsImpl = new UserDetailsImpl(userFixture);
  }

  @Test
  public void userDetailsImplShouldReturnInformationEqualToUser() {
    assertThat(userDetailsImpl.getUsername()).isNotNull().isEqualTo(userFixture.getUsername());
    assertThat(userDetailsImpl.getPassword()).isNotNull().isEqualTo(userFixture.getPassword());
    assertThat(userDetailsImpl.isEnabled()).isNotNull().isEqualTo(userFixture.getIsEnabled());
    assertThat(userDetailsImpl.isAccountNonLocked()).isNotNull().isEqualTo(!userFixture.getIsBlocked());
    assertThat(userDetailsImpl.isAccountNonExpired()).isNotNull().isTrue();
    assertThat(userDetailsImpl.isCredentialsNonExpired()).isNotNull().isTrue();
    assertThat(userDetailsImpl.getAuthorities()).isNotNull().isEqualTo(userFixture.getGrantedAuthorities());
  }
}