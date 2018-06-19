package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

public class BlacklistedIpTest {

  private static final String START_IP = "127.0.0.1";
  private static final String END_IP = "127.0.0.255";

  @Spy
  private BlacklistedIp fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void blacklistedIpShouldBeAnnotated() {
    assertThat(BlacklistedIp.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(BlacklistedIp.class.getDeclaredAnnotation(Table.class).name())
        .isEqualTo("blacklisted_ips");
  }

  @Test
  public void blacklistedIpShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(BlacklistedIp.class);
  }

  @Test
  public void blacklistedIpShouldHaveNoArgsConstructor() {
    assertThat(new BlacklistedIp()).isNotNull();
  }

  @Test
  public void blacklistedIpShouldHaveAllArgsConstructor() {
    assertThat(new BlacklistedIp(START_IP, END_IP)).hasFieldOrPropertyWithValue("startIp", START_IP)
        .hasFieldOrPropertyWithValue("endIp", END_IP);
  }

  @Test
  public void blacklistedIpShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void blacklistedIpShouldHaveStartIpGetterAndSetter() {
    fixture.setStartIp(START_IP);

    assertThat(fixture).hasFieldOrPropertyWithValue("startIp", START_IP);
    assertThat(fixture.getStartIp()).isEqualTo(START_IP);
  }

  @Test
  public void blacklistedIpShouldHaveEndIpGetterAndSetter() {
    fixture.setEndIp(END_IP);

    assertThat(fixture).hasFieldOrPropertyWithValue("endIp", END_IP);
    assertThat(fixture.getEndIp()).isEqualTo(END_IP);
  }
}
