package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
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

public class AuthorityTest {

  private static final String TEST_AUTHORITY_NAME = RandomStringUtils.random(11);

  @Spy
  private Authority fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void authorityShouldBeAnnotated() {
    assertThat(Authority.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(Authority.class.getDeclaredAnnotation(Table.class).name())
      .isEqualTo("client_authorities");
  }

  @Test
  public void authorityShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(Authority.class);
  }

  @Test
  public void authorityShouldHaveNoArgsConstructor() {
    assertThat(new Authority()).isNotNull();
  }

  @Test
  public void authorityShouldHaveAllArgsConstructor() {
    assertThat(new Authority(TEST_AUTHORITY_NAME)).hasFieldOrPropertyWithValue("name",
      TEST_AUTHORITY_NAME);
  }

  @Test
  public void authorityShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void authorityShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void authorityShouldHaveClientsGetterAndSetter() {
    Set<ClientAuthorityAssociation> associations = new HashSet<>(
      Collections.singletonList(Mockito.mock(ClientAuthorityAssociation.class)));

    fixture.setClientsWithAuthorities(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithAuthorities", associations);
    assertThat(fixture.getClientsWithAuthorities()).isEqualTo(associations);
  }

  @Test
  public void getAuthorityShouldReturnSecurityRole() {
    ReflectionTestUtils.setField(fixture, "name", TEST_AUTHORITY_NAME);

    assertThat(fixture.getAuthority()).isEqualTo(TEST_AUTHORITY_NAME);
  }
}
