package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;

public class AuthorityTest {
  private static final String TEST_AUTHORITY_NAME = "TEST";

  @Test
  public void roleShouldBeAnnotated() {
    assertThat(Authority.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Authority.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_authorities");
    assertThat(Authority.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(Authority.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void roleShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(Authority.class);
  }

  @Test
  public void constructorShouldCreateNewInstanceWithArguments() {
    assertThat(new Authority(TEST_AUTHORITY_NAME)).hasFieldOrPropertyWithValue("name", TEST_AUTHORITY_NAME);
  }
}