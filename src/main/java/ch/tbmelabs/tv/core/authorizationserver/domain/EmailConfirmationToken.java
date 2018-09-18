package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "email_confirmation_tokens")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConfirmationToken extends AbstractAuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "email_confirmation_tokens_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Size(min = 36, max = 36)
  @Column(columnDefinition = "bpchar(36)", unique = true, updatable = false)
  private String tokenString;

  @NotNull
  @Column(updatable = false)
  private Date expirationDate;

  @JsonManagedReference
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  public EmailConfirmationToken(String tokenString, User user) {
    setTokenString(tokenString);
    setUser(user);
    setExpirationDate(calculateExpirationDate());
  }

  private Date calculateExpirationDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    EmailConfirmationToken other = (EmailConfirmationToken) o;
    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
