package ch.tbmelabs.tv.webapp.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.webapp.model.Account;

@Service
public class EmailService {
  private static final Logger LOGGER = LogManager.getLogger(EmailService.class);

  @Value("${spring.mail.username}")
  private String senderAddress;

  @Autowired
  private JavaMailSender mailSender;

  private EmailService() {
    // Hidden constructor
  }

  public EmailService setSenderAddress(String address) {
    this.senderAddress = address;
    return this;
  }

  public void sendMail(Account receiver, String subject, String htmlMessage) {
    LOGGER.info("Sending email from " + senderAddress + " to " + receiver.getEmail());

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