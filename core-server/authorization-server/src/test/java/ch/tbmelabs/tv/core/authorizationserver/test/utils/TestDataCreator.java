package ch.tbmelabs.tv.core.authorizationserver.test.utils;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class TestDataCreator {
  private UserCRUDRepository userRepository;
  private RoleCRUDRepository authorityRepository;

  public TestDataCreator() {
    userRepository = ApplicationContextHolder.getApplicationContext().getBean(UserCRUDRepository.class);
    authorityRepository = ApplicationContextHolder.getApplicationContext().getBean(RoleCRUDRepository.class);
  }

  public User createTestUser(User newUser, boolean deleteExisting) {
    if (deleteExisting) {
      deleteExisting();
    }

    if (authorityRepository.findByName(SecurityRole.USER) == null) {
      authorityRepository.save(new Role(SecurityRole.USER));
    }

    return userRepository.save(newUser);
  }

  public User createTestUserWithRole(User newUser, Role newRole, boolean deleteExisting) {
    if (deleteExisting) {
      deleteExisting();
    }

    authorityRepository.save(newRole);

    return createTestUser(newUser, false);
  }

  private void deleteExisting() {
    authorityRepository.deleteAll();
    userRepository.deleteAll();
  }
}