package ch.tbmelabs.tv.core.authorizationserver.security.csrf;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

public class CsrfTokenRepository {
  private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";

  private static HttpSessionCsrfTokenRepository repository = null;

  public static HttpSessionCsrfTokenRepository getRepository() {
    if (repository == null) {
      repository = new HttpSessionCsrfTokenRepository();
      repository.setHeaderName(CSRF_HEADER_NAME);
    }

    return repository;
  }
}