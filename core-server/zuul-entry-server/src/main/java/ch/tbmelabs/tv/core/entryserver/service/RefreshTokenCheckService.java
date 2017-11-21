package ch.tbmelabs.tv.core.entryserver.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.entryserver.security.urlconnection.FormDataURLConnection;
import ch.tbmelabs.tv.core.entryserver.utils.AuthenticationRequestUtils;

@Service
public class RefreshTokenCheckService {
  private static final Logger LOGGER = LogManager.getLogger(RefreshTokenCheckService.class);

  private static final String SCOPE = "scope";
  private static final String GRANT_TYPE = "grant_type";
  private static final String REFRESH_TOKEN = "refresh_token";

  @Value("${security.oauth2.client.clientId}")
  private String clientId;

  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;

  @Value("${security.oauth2.client.scope}")
  private String clientScope;

  @Value("${security.oauth2.client.access-token-uri}")
  private String oauth2TokenEndpointUrl;

  @PostConstruct
  public void postConstruct() {
    if (clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Zuul entryserver must be a trusted client!");
    }

    if (!StringUtils.containsIgnoreCase(oauth2TokenEndpointUrl, "HTTPS")) {
      LOGGER.warn("Connection to Authorization server is not secure!");
    }
  }

  public boolean authenticateWithRefreshToken(String refreshToken, HttpServletResponse response)
      throws MalformedURLException, IOException {
    LOGGER.info("Sending refresh token '" + refreshToken + "' to endpoint " + oauth2TokenEndpointUrl);

    HttpURLConnection connection = new FormDataURLConnection<>(oauth2TokenEndpointUrl, clientId, clientSecret)
        .addFormField(SCOPE, clientScope).addFormField(GRANT_TYPE, REFRESH_TOKEN)
        .addFormField(REFRESH_TOKEN, refreshToken).connect();

    LOGGER.debug("Connection to endpoint '" + oauth2TokenEndpointUrl + "' established with HTTP status "
        + connection.getResponseCode() + ":" + connection.getResponseMessage());

    if (connection.getResponseCode() == HttpStatus.OK.value()) {
      AuthenticationRequestUtils.transformJSONBodyToHeaderValues(connection.getInputStream(), response);
      return true;
    }

    return false;
  }
}