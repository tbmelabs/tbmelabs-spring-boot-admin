package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Optional;
import java.util.Random;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.stereotype.Service;
import ch.tbmelabs.serverconstants.security.UserRoleEnum;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.RoleMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.impl.UserMailServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.impl.UserSignupServiceImpl;

public class UserSignupServiceTest {

  private static final String SIGNUP_FAILED_ERROR_MESSAGE =
      "An error occured. Please check your details!";
  private static final String DEFAULT_ROLE_NOT_FOUND_ERROR_MESSAGE =
      "Unable to find default authority \'" + UserRoleEnum.USER.getAuthority() + "\'!";

  private final MockEnvironment mockEnvironment = new MockEnvironment();

  @Mock
  private ApplicationContext mockApplicationContext;

  @Mock
  private UserCRUDRepository mockUserRepository;

  @Mock
  private RoleCRUDRepository mockRoleRepository;

  @Mock
  private RoleMapper mockRoleMapper;

  @Mock
  private UserService mockUserService;

  @Mock
  private UserMapper mockUserMapper;

  @Spy
  @InjectMocks
  private UserSignupServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(mockEnvironment).when(mockApplicationContext).getEnvironment();
    doReturn(Mockito.mock(UserMailServiceImpl.class)).when(mockApplicationContext)
        .getBean(UserMailServiceImpl.class);

    doReturn(Optional.of(new Role(UserRoleEnum.USER.getAuthority()))).when(mockRoleRepository)
        .findByName(UserRoleEnum.USER.getAuthority());

    doAnswer((Answer<RoleDTO>) invocation -> {
      RoleDTO dto = new RoleDTO();
      dto.setName(((Role) invocation.getArgument(0)).getName());
      return dto;
    }).when(mockRoleMapper).toDto(Mockito.any(Role.class));

    doAnswer((Answer<User>) invocation -> {
      User user = new User();
      user.setId(new Random().nextLong());
      return user;
    }).when(mockUserService).save(Mockito.any(UserDTO.class));

    doReturn(true).when(fixture).isUsernameUnique(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(fixture).doesUsernameMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(fixture).isEmailAddressUnique(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(fixture).isEmailAddress(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(fixture).doesPasswordMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doReturn(true).when(fixture).doPasswordsMatch(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void userSignupServiceShouldBeAnnotated() {
    assertThat(UserSignupServiceImpl.class).hasAnnotation(Service.class);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnUsernameNotUnique() {
    doReturn(false).when(fixture).isUsernameUnique(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnUsernameWrongFormat() {
    doReturn(false).when(fixture).doesUsernameMatchFormat(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnEmailNotUnique() {
    doReturn(false).when(fixture).isEmailAddressUnique(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnEmailWrongFormat() {
    doReturn(false).when(fixture).isEmailAddress(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnPasswordWrongFormat() {
    doReturn(false).when(fixture).doesPasswordMatchFormat(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnPasswordsDoNotMatch() {
    doReturn(false).when(fixture).doPasswordsMatch(ArgumentMatchers.any(UserDTO.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class).hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  // TODO: Reimplement
  @Test
  @Ignore
  public void userSignupServiceShouldThrowErrorIfUserRoleDoesNotExist() {
    doReturn(Optional.empty()).when(mockRoleRepository)
        .findByName(UserRoleEnum.USER.getAuthority());

    assertThatThrownBy(() -> fixture.signUpNewUser(new UserDTO()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(DEFAULT_ROLE_NOT_FOUND_ERROR_MESSAGE);
  }

  // TODO: Reimplement
  @Test
  @Ignore
  public void userSignupServiceShouldSaveValidUser() {
    doReturn(Optional.of(new Role(UserRoleEnum.USER.getAuthority()))).when(mockRoleRepository)
        .findByName(UserRoleEnum.USER.getAuthority());

    fixture.signUpNewUser(new UserDTO());

    verify(mockUserMapper, times(1)).toDto(Mockito.any(User.class));
  }
}
