package ch.tbmelabs.tv.webapp.model.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.AccountRegistrationToken;
import ch.tbmelabs.tv.webapp.model.AuthenticationLog;
import ch.tbmelabs.tv.webapp.model.PasswordResetToken;
import ch.tbmelabs.tv.webapp.model.Role;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Account.class);
    config.exposeIdsFor(Role.class);

    config.exposeIdsFor(AuthenticationLog.class);

    config.exposeIdsFor(AccountRegistrationToken.class);
    config.exposeIdsFor(PasswordResetToken.class);
  }
}