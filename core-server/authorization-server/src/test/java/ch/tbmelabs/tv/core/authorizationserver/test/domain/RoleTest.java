package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;
import javax.persistence.Table;

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
  public void constructorShouldCreateNewInstanceWithArguments() {
    assertThat(new Role(TEST_ROLE_NAME)).hasFieldOrPropertyWithValue("name", TEST_ROLE_NAME);
  }
}