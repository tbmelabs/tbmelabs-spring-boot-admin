package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;

public class UserTest {
  private static final String TEST_USER_ROLE = "TEST";

  @Mock
  private User fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(fixture.getId()).thenReturn(new Random().nextLong());

    doCallRealMethod().when(fixture).rolesToAssociations(Mockito.anyList());
  }

  @Test
  public void userShouldBeAnnotated() {
    assertThat(User.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(User.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("users");
    assertThat(User.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(User.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void userShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(User.class);
  }

  @Test
  public void newUserShouldHaveDefaultValues() {
    User fixture = new User();

    assertThat(fixture.getIsEnabled()).isTrue();
    assertThat(fixture.getIsBlocked()).isFalse();
  }

  @Test
  public void userShouldConvertRoleToUserRoleAssociation() {
    List<UserRoleAssociation> newAssociation = (List<UserRoleAssociation>) fixture
        .rolesToAssociations(Arrays.asList(new Role(TEST_USER_ROLE)));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getUser()).isNotNull().isEqualTo(fixture);
    assertThat(newAssociation.get(0).getUserRole().getName()).isNotNull().isEqualTo(TEST_USER_ROLE);
  }
}