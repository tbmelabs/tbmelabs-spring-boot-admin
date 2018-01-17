package ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Component
@Profile({ SpringApplicationProfile.TEST })
public class TestRoleManager {
  private Role test;

  @Autowired
  private RoleCRUDRepository roleRepository;

  private TestRoleManager() {
    // Hidden constructor
  }

  @PostConstruct
  public void initBean() {
    test = createTestRole();
  }

  private Role createTestRole() {
    Role testRole = new Role();
    testRole.setName("TEST");

    return roleRepository.save(testRole);
  }

  public Role getTestRole() {
    return test;
  }
}