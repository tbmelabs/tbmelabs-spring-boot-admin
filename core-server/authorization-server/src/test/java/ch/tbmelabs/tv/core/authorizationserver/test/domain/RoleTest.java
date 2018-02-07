package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;

public class RoleTest {
  private static final String TEST_ROLE_NAME = "TEST";

  @Test
  public void roleShouldBeAnnotated() {
    assertThat(Role.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Role.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("user_roles");
    assertThat(Role.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(Role.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
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
    assertThat(new Role(TEST_ROLE_NAME)).hasFieldOrPropertyWithValue("name", TEST_ROLE_NAME);
  }

  @Test
  public void roleTypeShouldHaveIdGetterAndSetter() {
    Role fixture = new Role();
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void roleShouldHaveNameGetterAndSetter() {
    Role fixture = new Role();
    String name = RandomStringUtils.randomAlphabetic(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }
}