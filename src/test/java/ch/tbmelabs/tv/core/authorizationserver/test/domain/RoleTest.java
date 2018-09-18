package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

public class RoleTest {

  private static final String TEST_ROLE_NAME = RandomStringUtils.random(11);

  @Spy
  private Role fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void roleShouldBeAnnotated() {
    assertThat(Role.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(Role.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("user_roles");
  }

  @Test
  public void roleShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(Role.class);
  }

  @Test
  public void roleShouldHaveNoArgsConstructor() {
    assertThat(new Role()).isNotNull();
  }

  @Test
  public void roleShouldHaveAllArgsConstructor() {
    assertThat(new Role(TEST_ROLE_NAME)).hasFieldOrPropertyWithValue("name", TEST_ROLE_NAME);
  }

  @Test
  public void roleTypeShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void roleShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void roleShouldHaveUserGetterAndSetter() {
    Set<UserRoleAssociation> associations =
      new HashSet<>(Collections.singletonList(Mockito.mock(UserRoleAssociation.class)));

    fixture.setUsersWithRoles(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("usersWithRoles", associations);
    assertThat(fixture.getUsersWithRoles()).isEqualTo(associations);
  }

  @Test
  public void getAuthorityShouldReturnSecurityRole() {
    ReflectionTestUtils.setField(fixture, "name", TEST_ROLE_NAME);

    assertThat(fixture.getAuthority()).isEqualTo(TEST_ROLE_NAME);
  }
}
