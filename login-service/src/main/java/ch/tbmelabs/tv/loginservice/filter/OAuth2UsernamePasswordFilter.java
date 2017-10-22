package ch.tbmelabs.tv.loginservice.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ch.tbmelabs.tv.loginservice.utils.FormDataURLConnector;

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

    // TODO: Secure connection if not in docker environment
    // if (!StringUtils.containsIgnoreCase(authorizationProcessingUrl, "HTTPS"))
    // {
    // throw new IllegalArgumentException("OAuth2 URL must be secure (HTTPS)!");
    // }

    if (clientId.isEmpty() || clientSecret.isEmpty()) {
      throw new IllegalArgumentException("Client must be identified be id and secret / login client must be trusted!");
    }
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {
    LOGGER.info("Applying " + OAuth2UsernamePasswordFilter.class);

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    HttpURLConnection connection = new FormDataURLConnector<HttpURLConnection>(authorizationProcessingUrl)
        .setBasicAuthorizationHeader(clientId, clientSecret).addFormField("username", request.getParameter("username"))
        .addFormField("password", request.getParameter("password")).addFormField("grant_type", "password").connect()
        .getResponse();

    forwardResponse(response, connection);

    connection.disconnect();
  }

  private void forwardResponse(HttpServletResponse response, HttpURLConnection connection) throws IOException {
    LOGGER.info("Response from " + connection.getURL().toString() + " is " + connection.getResponseCode() + ":"
        + connection.getResponseMessage());

    if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 226) {
      LOGGER.debug("Channeling " + InputStream.class + " to " + ServletOutputStream.class);

      byte[] buffer = new byte[2048];
      InputStream input = connection.getInputStream();
      OutputStream output = response.getOutputStream();

      for (int length = 0; (length = input.read(buffer)) > 0;) {
        output.write(buffer, 0, length);
      }

      response.setStatus(connection.getResponseCode());

      // TODO: Check if this is even required
      output.flush();
    } else {
      LOGGER.debug("Sending error from client");

      // TODO: Message is null?
      response.sendError(connection.getResponseCode(), connection.getResponseMessage());
      // response.sendError(connection.getResponseCode());
    }
  }
}