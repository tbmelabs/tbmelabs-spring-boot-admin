package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

public class RoleTest {

  private static final String TEST_AUTHORITY_NAME = "TEST";

  @Spy
  private Role fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void roleShouldBeAnnotated() {
    assertThat(Role.class).hasAnnotation(Entity.class).hasAnnotation(Table.class)
        .hasAnnotation(JsonInclude.class).hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Role.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("user_roles");
    assertThat(Role.class.getDeclaredAnnotation(JsonInclude.class).value())
        .isEqualTo(Include.NON_NULL);
    assertThat(Role.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown())
        .isTrue();
  }

  @Test
  public void roleShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(Role.class);
  }

  @Test
  public void roleShouldHaveNoArgsConstructor() {
    assertThat(new Role()).isNotNull();
  }

  @Test
  public void roleShouldHaveAllArgsConstructor() {
    assertThat(new Role(TEST_AUTHORITY_NAME)).hasFieldOrPropertyWithValue("name",
        TEST_AUTHORITY_NAME);
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
    List<UserRoleAssociation> associations = Arrays.asList(
        Mockito.mock(UserRoleAssociation.class));

    fixture.setUsersWithRoles(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("usersWithRoles", associations);
    assertThat(fixture.getUsersWithRoles()).isEqualTo(associations);
  }

  @Test
  public void getAuthorityShouldReturnSecurityRole() {
    ReflectionTestUtils.setField(fixture, "name", TEST_AUTHORITY_NAME);

    assertThat(fixture.getAuthority()).isEqualTo(TEST_AUTHORITY_NAME);
  }
}
