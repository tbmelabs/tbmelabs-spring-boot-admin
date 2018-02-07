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

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;

public class GrantTypeTest {
  private static final String TEST_GRANT_TYPE_NAME = "TEST";

  @Test
  public void grantTypeShouldBeAnnotated() {
    assertThat(GrantType.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(GrantType.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_grant_types");
    assertThat(GrantType.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(GrantType.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void grantTypeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(GrantType.class);
  }

  @Test
  public void grantTypeShouldHaveNoArgsConstructor() {
    assertThat(new GrantType()).isNotNull();
  }

  @Test
  public void grantTypeShouldHaveAllArgsConstructor() {
    assertThat(new GrantType(TEST_GRANT_TYPE_NAME)).hasFieldOrPropertyWithValue("name", TEST_GRANT_TYPE_NAME);
  }

  @Test
  public void grantTypeShouldHaveIdGetterAndSetter() {
    GrantType fixture = new GrantType();
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void grantTypeShouldHaveNameGetterAndSetter() {
    GrantType fixture = new GrantType();
    String name = RandomStringUtils.randomAlphabetic(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }
}