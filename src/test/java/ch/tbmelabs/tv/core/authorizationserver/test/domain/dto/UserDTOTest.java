package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class UserDTOTest {

  public static User createTestUser() {
    User testUser = new User();
    testUser.setUsername(RandomStringUtils.randomAlphabetic(11));
    testUser.setEmail(testUser.getUsername() + "@tbme.tv");
    testUser.setPassword(RandomStringUtils.random(60));
    testUser.setConfirmation(testUser.getPassword());

    return testUser;
  }

  @Test
  public void userDTOShouldHavePublicConstructor() {
    assertThat(new UserDTO()).isNotNull();
  }
}
