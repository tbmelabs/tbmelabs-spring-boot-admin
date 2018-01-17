package ch.tbmelabs.tv.core.authorizationserver.test.web.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;

public class LoggingExceptionTranslatorTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String TEST_EXCEPTION_MESSAGE = "This is just a test.";

  @Autowired
  private LoggingExceptionTranslator loggingExceptionTranslator;

  @Test
  public void loggingExceptionTranslatorShouldTranslateExceptionToResponseEntity() throws Exception {
    ResponseEntity<OAuth2Exception> response = loggingExceptionTranslator
        .translate(new Exception(TEST_EXCEPTION_MESSAGE));

    assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody().getCause()).isNotNull().isOfAnyClassIn(Exception.class)
        .hasMessage(TEST_EXCEPTION_MESSAGE);
    assertThat(response.getBody().getMessage()).isNotNull().isEqualTo(TEST_EXCEPTION_MESSAGE);
  }
}