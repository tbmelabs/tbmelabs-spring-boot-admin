package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserDTOTest;
import ch.tbmelabs.tv.core.authorizationserver.web.rest.UserController;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class UserControllerTest {

  @Mock
  private UserService mockUserService;

  @Mock
  private UserMapper mockUserMapper;

  @Spy
  @InjectMocks
  private UserController fixture;

  private UserDTO testUserDTO;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    User testUser = UserDTOTest.createTestUser();
    testUserDTO = new UserDTO();
    // testUserDTO.setId(testUser.getId());
    testUserDTO.setCreated(testUser.getCreated());
    testUserDTO.setLastUpdated(testUser.getLastUpdated());
    testUserDTO.setUsername(testUser.getUsername());
    testUserDTO.setEmail(testUser.getEmail());
    testUserDTO.setIsEnabled(testUser.getIsEnabled());
    testUserDTO.setIsBlocked(testUser.getIsBlocked());
    // TODO: Associations

    doReturn(new PageImpl<>(Collections.singletonList(testUserDTO))).when(mockUserService)
        .findAll(ArgumentMatchers.any(Pageable.class));

    doReturn(testUserDTO).when(mockUserMapper).toDto(ArgumentMatchers.any(User.class));
    doReturn(testUser).when(mockUserMapper).toEntity(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void userControllerShouldBeAnnotated() {
    assertThat(UserController.class).hasAnnotation(RestController.class)
        .hasAnnotation(RequestMapping.class).hasAnnotation(PreAuthorize.class);
    assertThat(UserController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("${spring.data.rest.base-path}/users");
    assertThat(UserController.class.getDeclaredAnnotation(PreAuthorize.class).value())
        .isEqualTo("hasAuthority('" + UserAuthority.SERVER_SUPPORT + "')");
  }

  @Test
  public void userControllerShouldHavePublicConstructor() {
    assertThat(new UserController()).isNotNull();
  }

  @Test
  public void getAllUsersShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method =
        UserController.class.getDeclaredMethod("getAllUsers", new Class<?>[]{Pageable.class});
    assertThat(method.getDeclaredAnnotation(GetMapping.class).value()).isEmpty();
  }

  @Test
  public void getAllUsersShouldReturnPageWithAllClients() {
    assertThat(fixture.getAllUsers(Mockito.mock(Pageable.class)).getContent()).hasSize(1)
        .containsExactly(testUserDTO);

    verify(mockUserService, times(1)).findAll(ArgumentMatchers.any(Pageable.class));
  }

  @Test
  public void updateUserShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method =
        UserController.class.getDeclaredMethod("updateUser", new Class<?>[]{UserDTO.class});
    assertThat(method.getDeclaredAnnotation(PutMapping.class).value()).isEmpty();
  }

  @Test
  public void updateUserShouldPersistMappedDTO() {
    testUserDTO.setId(new Random().nextLong());

    fixture.updateUser(testUserDTO);

    verify(mockUserService, times(1)).update(testUserDTO);
  }

  @Test
  public void deleteUserShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method =
        UserController.class.getDeclaredMethod("deleteUser", new Class<?>[]{UserDTO.class});
    assertThat(method.getDeclaredAnnotation(DeleteMapping.class).value()).isEmpty();
  }

  @Test
  public void deleteUserShouldDeleteMappedDTO() {
    testUserDTO.setId(new Random().nextLong());

    fixture.deleteUser(testUserDTO);

    verify(mockUserService, times(1)).delete(testUserDTO);
  }
}
