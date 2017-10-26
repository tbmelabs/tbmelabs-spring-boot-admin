package ch.tbmelabs.tv.services.loginservice.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ch.tbmelabs.tv.services.loginservice.utils.FormDataURLConnector;

@Component
public class OAuth2UsernamePasswordFilter extends GenericFilterBean {
  private static final Logger LOGGER = LogManager.getLogger(OAuth2UsernamePasswordFilter.class);

  @Value("${spring.oauth2.client.clientId}")
  private String clientId;

  @Value("${spring.oauth2.client.secret}")
  private String clientSecret;

  @Value("${spring.oauth2.client.accessTokenUri}")
  private String authorizationProcessingUrl;

  @Override
  protected void initFilterBean() throws ServletException {
    LOGGER.debug("Checking instanciated " + OAuth2UsernamePasswordFilter.class);

    if (!StringUtils.containsIgnoreCase(authorizationProcessingUrl, "HTTPS")) {
      LOGGER.warn("You are using an authorization server without SSL: This is very insecure!");
    }

    if (clientId.isEmpty() || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client must be identified be id and secret / login client must be trusted!");
    }
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {
    checkArguments(req);

    LOGGER.info("Applying " + OAuth2UsernamePasswordFilter.class);

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    HttpURLConnection connection = new FormDataURLConnector<HttpURLConnection>(authorizationProcessingUrl, clientId,
        clientSecret).addFormField("username", request.getParameter("username"))
            .addFormField("password", request.getParameter("password")).addFormField("grant_type", "password")
            .connect();

    forwardResponse(response, connection);

    connection.disconnect();
  }

  private void checkArguments(ServletRequest request) {
    LOGGER.debug("Checking request arguments");

    List<String> requestParameters = Collections.list(request.getParameterNames());

    if (!requestParameters.contains("username") || !requestParameters.contains("password")) {
      throw new IllegalArgumentException("Please login with username and password.");
    }
  }

  private void forwardResponse(HttpServletResponse response, HttpURLConnection connection) throws IOException {
    LOGGER.info("Response from " + connection.getURL().toString() + " is " + connection.getResponseCode() + ":"
        + connection.getResponseMessage());

    if (connection.getResponseCode() == HttpStatus.SC_OK) {
      LOGGER.debug("Channeling " + InputStream.class + " to " + ServletOutputStream.class);

      response.setStatus(connection.getResponseCode());
      response.setContentType(ContentType.APPLICATION_JSON.getMimeType());

      byte[] buffer = new byte[2048];
      InputStream input = connection.getInputStream();
      ServletOutputStream output = response.getOutputStream();

      for (int length = 0; (length = input.read(buffer)) > 0;) {
        output.write(buffer, 0, length);
      }

      input.close();
      output.close();
    } else {
      response.sendError(HttpStatus.SC_UNAUTHORIZED, "Bad credentials!");
    }
  }
}