package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;

public class BlacklistedIpTest {
  private static final String TEST_IP = "127.0.0.1";

  @Test
  public void blacklistedIpShouldBeAnnotated() {
    assertThat(BlacklistedIp.class).hasAnnotation(Entity.class).hasAnnotation(Table.class)
        .hasAnnotation(JsonInclude.class).hasAnnotation(JsonIgnoreProperties.class);

    assertThat(BlacklistedIp.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("blacklisted_ips");
    assertThat(BlacklistedIp.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(BlacklistedIp.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull()
        .isTrue();
  }

  @Test
  public void blacklistedIpShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(BlacklistedIp.class);
  }

  @Test
  public void blacklistedIpShouldHaveNoArgsConstructor() {
    assertThat(new BlacklistedIp()).isNotNull();
  }

  @Test
  public void blacklistedIpShouldHaveAllArgsConstructor() {
    assertThat(new BlacklistedIp(TEST_IP)).hasFieldOrPropertyWithValue("ip", TEST_IP);
  }

  @Test
  public void blacklistedIpShouldHaveIdGetterAndSetter() {
    BlacklistedIp fixture = new BlacklistedIp();
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void blacklistedIpShouldHaveIpGetterAndSetter() {
    BlacklistedIp fixture = new BlacklistedIp();
    String ip = "127.0.0.1";

    fixture.setIp(ip);

    assertThat(fixture).hasFieldOrPropertyWithValue("ip", ip);
    assertThat(fixture.getIp()).isEqualTo(ip);
  }
}