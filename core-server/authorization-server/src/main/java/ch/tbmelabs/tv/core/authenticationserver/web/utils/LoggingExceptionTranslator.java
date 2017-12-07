package ch.tbmelabs.tv.core.authenticationserver.web.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class LoggingExceptionTranslator extends DefaultWebResponseExceptionTranslator {
  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
    ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
    HttpHeaders headers = new HttpHeaders();
    headers.setAll(responseEntity.getHeaders().toSingleValueMap());
    OAuth2Exception excBody = responseEntity.getBody();
    return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
  }
}