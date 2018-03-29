package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserProfileMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserProfileTest;
import ch.tbmelabs.tv.core.authorizationserver.test.web.rest.UserControllerIntTest;

public class UserProfileMapperTest {
  @Mock
  private UserRoleAssociationCRUDRepository userRoleAssociationRepository;

  @Spy
  @InjectMocks
  private UserProfileMapper fixture;

  private User testUser;
  private UserProfile testUserProfile;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    testUser = UserProfileTest.createTestUser();

    testUserProfile = UserControllerIntTest.createTestUserProfile();
    testUserProfile.setId(new Random().nextLong());

    doReturn(new ArrayList<>()).when(userRoleAssociationRepository).findAllByUser(testUser);
  }

  @Test
  public void userProfileMapperShouldBeAnnotated() {
    assertThat(UserProfileMapper.class).hasAnnotation(Component.class);
  }

  @Test
  public void userProfileMapperShouldHavePublicConstructor() {
    assertThat(new UserProfileMapper()).isNotNull();
  }

  @Test
  public void toUserProfileShouldMapUserToDTO() {
    assertThat(fixture.toUserProfile(testUser)).hasFieldOrPropertyWithValue("created", testUser.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testUser.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testUser.getId())
        .hasFieldOrPropertyWithValue("username", testUser.getUsername())
        .hasFieldOrPropertyWithValue("email", testUser.getEmail())
        .hasFieldOrPropertyWithValue("isEnabled", testUser.getIsEnabled())
        .hasFieldOrPropertyWithValue("isBlocked", testUser.getIsBlocked())
        .hasFieldOrPropertyWithValue("roles", new ArrayList<>());

    assertThat(fixture.toUserProfile(testUser).getPassword()).isNull();
  }

  @Test
  public void toUserShouldMapDTOToEntity() {
    assertThat(fixture.toUser(testUserProfile)).hasFieldOrPropertyWithValue("created", testUserProfile.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testUserProfile.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testUserProfile.getId())
        .hasFieldOrPropertyWithValue("username", testUserProfile.getUsername())
        .hasFieldOrPropertyWithValue("email", testUserProfile.getEmail())
        .hasFieldOrPropertyWithValue("password", testUserProfile.getPassword())
        .hasFieldOrPropertyWithValue("confirmation", testUserProfile.getConfirmation())
        .hasFieldOrPropertyWithValue("isEnabled", testUserProfile.getIsEnabled())
        .hasFieldOrPropertyWithValue("isBlocked", testUserProfile.getIsBlocked())
        .hasFieldOrPropertyWithValue("roles", new ArrayList<>());
  }
}