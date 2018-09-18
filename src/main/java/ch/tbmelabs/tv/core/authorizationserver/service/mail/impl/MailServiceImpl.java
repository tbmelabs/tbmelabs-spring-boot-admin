package ch.tbmelabs.tv.core.authorizationserver.service.mail.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;
import ch.tbmelabs.tv.core.authorizationserver.configuration.ApplicationProperties;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.MailService;

@Service
@Profile({"!" + SpringApplicationProfileConstants.NO_MAIL})
public class MailServiceImpl implements MailService {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

  private JavaMailSender mailSender;

  private String senderAddress;

  public MailServiceImpl(JavaMailSender javaMailSender,
      ApplicationProperties applicationProperties) {
    this.mailSender = javaMailSender;

    this.senderAddress = applicationProperties.getSpring().getMail().getUsername();
  }

  public void sendMail(User receiver, String subject, String htmlMessage) {
    LOGGER.info("Sending email from {} to {}", senderAddress, receiver.getEmail());

    try {
      MimeMessage message = mailSender.createMimeMessage();
      message.setSubject(subject);

      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(senderAddress);
      helper.setTo(receiver.getEmail());

      helper.setText(htmlMessage, true);
      mailSender.send(message);

    } catch (MessagingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
