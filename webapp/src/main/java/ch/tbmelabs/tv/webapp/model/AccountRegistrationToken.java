package ch.tbmelabs.tv.webapp.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.tbmelabs.tv.webapp.model.repository.AccountRegistrationTokenCRUDRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude = { "token" })
@Table(name = "account_registration_token")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class AccountRegistrationToken extends NicelyDocumentedEntity {
  @NotEmpty
  @JsonIgnore
  @Column(name = "token_string", columnDefinition = "CHAR(36)")
  protected String token;

  @NotNull
  @Column(name = "expiration_date")
  protected Date expirationDate;

  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
  @JoinColumn(name = "a_id")
  protected Account account;

  @PrePersist
  public void onCreate() {
    super.onCreate();

    Calendar calendarInstance = Calendar.getInstance();
    calendarInstance.set(Calendar.DAY_OF_YEAR, calendarInstance.get(Calendar.DAY_OF_YEAR) + 1);
    expirationDate = calendarInstance.getTime();
  }

  public static class AccountRegistrationTokenRemover extends TimerTask {
    protected static final Logger LOGGER = LogManager.getLogger(AccountRegistrationTokenRemover.class);

    protected AccountRegistrationTokenCRUDRepository repository;
    protected AccountRegistrationToken token;

    public AccountRegistrationTokenRemover(AccountRegistrationTokenCRUDRepository repository,
        AccountRegistrationToken token) {
      LOGGER.info("Scheduling task to remove token " + token);

      this.repository = repository;
      this.token = token;
    }

    @Override
    public void run() {
      LOGGER.info("Deleting account reset token " + token);

      repository.delete(token.getId());
    }
  }
}