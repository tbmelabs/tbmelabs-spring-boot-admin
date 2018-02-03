package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;

public class ScopeTest {
  private static final String TEST_SCOPE_NAME = "TEST";

  @Test
  public void scopeShouldBeAnnotated() {
    assertThat(Scope.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Scope.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_scopes");
    assertThat(Scope.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(Scope.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void scopeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(Scope.class);
  }

  @Test
  public void constructorShouldCreateNewInstanceWithArguments() {
    assertThat(new Scope(TEST_SCOPE_NAME)).hasFieldOrPropertyWithValue("name", TEST_SCOPE_NAME);
  }
}