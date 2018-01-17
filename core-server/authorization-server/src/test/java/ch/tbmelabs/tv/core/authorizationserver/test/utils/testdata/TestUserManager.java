package ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Component
@DependsOn({ "defaultDataService" })
@Profile({ SpringApplicationProfile.TEST })
public class TestUserManager {
  private static final String VALID_PASSWORD = "UserPassword99$";

  private User user;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private TestRoleManager testRoleManager;

  private TestUserManager() {
    // Hidden constructor
  }

  @PostConstruct
  public void initBean() {
    user = createUser();
  }

  private User createUser() {
    User userUser = new User();
    userUser.setUsername("Testuser");
    userUser.setEmail("test.user@tbme.tv");
    userUser.setPassword(VALID_PASSWORD);
    userUser.setConfirmation(VALID_PASSWORD);
    userUser.setGrantedAuthorities(userUser.rolesToAssociations(
        Arrays.asList(roleRepository.findByName(SecurityRole.USER), testRoleManager.getTestRole())));

    return userRepository.save(userUser);
  }

  public User getUserUser() {
    return user;
  }
}