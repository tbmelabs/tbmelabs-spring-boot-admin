package ch.tbmelabs.tv.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@Table(name = "account")
@ToString(exclude = { "password", "passwordMatch" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends NicelyDocumentedEntity {
  @Transient
  @JsonIgnore
  // At least one lower- and ONE UPPERCASE letter
  // At least one digit
  // At least one special char
  // No whitespaces at all
  // 8 to 32 characters allowed
  private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}$";

  @Transient
  @JsonIgnore
  public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Override
  @PrePersist
  public void onCreate() {
    super.onCreate();

    setIsEmailApproved(false);
    setIsBlocked(false);

    if (doesPasswordMatchFormat() && doPasswordsMatch()) {
      setPassword(PASSWORD_ENCODER.encode(getPassword()));
    }
  }

  @PostLoad
  public void onLoad() {
    setPasswordMatch(getPassword());
  }

  @Override
  @PreUpdate
  public void onUpdate() {
    super.onUpdate();

    if (!getPassword().equals(getPasswordMatch()) && doesPasswordMatchFormat()) {
      setPassword(PASSWORD_ENCODER.encode(getPassword()));
    }
  }

  @NotEmpty
  @Column(columnDefinition = "VARCHAR(32)")
  private String username;

  @NotEmpty
  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(columnDefinition = "CHAR(60)")
  private String password;

  @NotEmpty
  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private String passwordMatch;

  @NotEmpty
  @Email
  @Column(columnDefinition = "VARCHAR(64)")
  private String email;

  @NotNull
  @Column(name = "is_email_approved")
  private Boolean isEmailApproved;

  @NotNull
  @Column(name = "is_blocked")
  private Boolean isBlocked;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
  @JoinColumn(name = "al_id")
  private Role accessLevel;

  // @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE },
  // mappedBy = "account")
  // @JsonIgnoreProperties({ "account" })
  // private PasswordResetToken passwordReset;

  public boolean doesPasswordMatchFormat() {
    if (!getPassword().matches(PASSWORD_PATTERN)) {
      throw new IllegalArgumentException(
          "Password must match given format: One of each upper- and lowercase letter, digit, special char and at least 8 characters!");
    }

    return true;
  }

  public boolean doPasswordsMatch() {
    if (!getPassword().equals(getPasswordMatch())) {
      throw new IllegalArgumentException("Passwords do not match!");
    }

    return true;
  }
}