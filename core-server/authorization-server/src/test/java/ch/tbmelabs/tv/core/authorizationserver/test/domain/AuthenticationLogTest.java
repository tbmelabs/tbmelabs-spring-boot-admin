package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;

public class AuthenticationLogTest {
  private static final AUTHENTICATION_STATE TEST_AUTHENTICATION_STATE = AUTHENTICATION_STATE.OK;
  private static final String TEST_IP = "127.0.0.1";
  private static final String TEST_MESSAGE = "This is some message.";

  @Mock
  private User userFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void authenticationLogShouldBeAnnotated() {
    assertThat(AuthenticationLog.class).hasAnnotation(Entity.class).hasAnnotation(Table.class)
        .hasAnnotation(JsonInclude.class).hasAnnotation(JsonIgnoreProperties.class);

    assertThat(AuthenticationLog.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
        .isEqualTo("authentication_log");
    assertThat(AuthenticationLog.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(AuthenticationLog.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull()
        .isTrue();
  }

  @Test
  public void authenticationLogShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(AuthenticationLog.class);
  }

  @Test
  public void constructorShouldCreateNewInstanceWithArguments() {
    assertThat(new AuthenticationLog(TEST_AUTHENTICATION_STATE, TEST_IP, TEST_MESSAGE, userFixture))
        .hasFieldOrPropertyWithValue("state", TEST_AUTHENTICATION_STATE.name())
        .hasFieldOrPropertyWithValue("ip", TEST_IP).hasFieldOrPropertyWithValue("message", TEST_MESSAGE)
        .hasFieldOrPropertyWithValue("user", userFixture);
  }
}