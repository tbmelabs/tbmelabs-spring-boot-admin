package ch.tbmelabs.tv.core.authorizationserver.service.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.web.signup.SignupConfirmationController;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Service
@Profile({ "!" + SpringApplicationProfile.NO_MAIL })
public class UserMailService extends MailService {
  private static final String TEMPLATE_FOLDER = "email-templates";
  private static final String SIGNUP_MAIL_TEMPLATE_LOCATION = TEMPLATE_FOLDER + "/signup.email.html";

  private static final String USERNAME_REPLACEMENT = "%USERNAME%";
  private static final String CONFIRMATION_URL_REPLACEMENT = "%CONFIRMATION_URL%";

  @Value("${server.address}")
  private String serverAddress;

  @Value("${server.port:80}")
  private Integer serverPort;

  @Value("${server.context-path:}")
  private String contextPath;

  public void sendSignupConfirmation(User user) {
    LOGGER.info("Sending sign up confirmation to " + user.getEmail());

    try {
      String emailBody = loadFileContent(
          new File(UserMailService.class.getClassLoader().getResource(SIGNUP_MAIL_TEMPLATE_LOCATION).toURI()));

      final String confirmationToken = createUniqueConfirmationToken();

      emailBody = emailBody.replaceAll(USERNAME_REPLACEMENT, user.getUsername());
      emailBody = emailBody.replaceAll(CONFIRMATION_URL_REPLACEMENT, getConfirmationUrl(confirmationToken));

      sendMail(user, "Confirm registration to TBME Labs", emailBody);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String loadFileContent(File file) {
    LOGGER.debug("Loading file content from " + file.getAbsolutePath());

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      StringBuilder htmlBuilder = new StringBuilder();
      String htmlLine;
      while ((htmlLine = reader.readLine()) != null) {
        htmlBuilder.append(htmlLine);
      }

      return htmlBuilder.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String createUniqueConfirmationToken() {
    // TODO: Check uniqueness in repository
    return UUID.randomUUID().toString();
  }

  private String getConfirmationUrl(String token) {
    String confirmationUrl = "https://" + serverAddress + ":" + serverPort + contextPath
        + SignupConfirmationController.CONFIRMATION_ENDPOINT + "?" + SignupConfirmationController.TOKEN_ARGUMENT_NAME
        + "=" + token;

    LOGGER.debug("Created confirmation url " + confirmationUrl);

    return confirmationUrl;
  }
}