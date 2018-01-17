package ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Service
@Profile({ SpringApplicationProfile.TEST })
public class TestUserService {
  private static final Logger LOGGER = LogManager.getLogger(TestUserService.class);

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository authorityRepository;

  private TestUserService() {
    // Hidden constructor
  }

  @PostConstruct
  public void initBean() {
    scanAndCreateRequestedTestUsers();
  }

  private void scanAndCreateRequestedTestUsers() {
    new Reflections(AbstractOAuth2AuthorizationApplicationContextAware.class.getPackage().getName())
        .getTypesAnnotatedWith(CreateTestUser.class).forEach(clazz -> {
          LOGGER.debug("Found " + CreateTestUser.class + " in class " + clazz);

          CreateTestUser configuration = clazz.getDeclaredAnnotation(CreateTestUser.class);
          saveNewTestUser(configuration.username(), configuration.email(), configuration.password(),
              configuration.confirmation(), configuration.isEnabled(), configuration.isBlocked(),
              configuration.authorities());
        });
  }

  private void saveNewTestUser(String username, String email, String password, String confirmation, boolean isEnabled,
      boolean isBlocked, String[] authorities) {
    if (userRepository.findByUsername(username) != null) {
      return;
    }

    User newTestUser = new User();
    newTestUser.setUsername(username);
    newTestUser.setEmail(email);
    newTestUser.setPassword(password);
    newTestUser.setConfirmation(confirmation);
    newTestUser.setIsEnabled(isEnabled);
    newTestUser.setIsBlocked(isBlocked);

    List<Role> newTestRoles = Arrays.stream(authorities).map(name -> new Role(name))
        .filter(newRole -> authorityRepository.findByName(newRole.getName()) == null).map(authorityRepository::save)
        .collect(Collectors.toList());

    if (!newTestRoles.isEmpty()) {
      newTestUser.setGrantedAuthorities(newTestUser.rolesToAssociations(newTestRoles));
    }

    LOGGER.info("Creating test user " + newTestUser);

    userRepository.save(newTestUser);
  }
}