package ch.tbmelabs.tv.core.authorizationserver.service.mail;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

public interface MailService {
  void sendMail(User receiver, String subject, String htmlMessage);
}
