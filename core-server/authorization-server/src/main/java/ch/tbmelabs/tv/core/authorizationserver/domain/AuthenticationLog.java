package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "authentication_log")
public class AuthenticationLog extends AuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  public enum AUTHENTICATION_STATE {
    OK, NOK
  }

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "authentication_log_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotNull
  @Column(columnDefinition = "varchar(3)", updatable = false)
  private AUTHENTICATION_STATE state;

  @NotEmpty
  @Length(max = 45)
  @Column(columnDefinition = "bpchar(45)", updatable = false)
  private String ip;

  @Length(max = 256)
  private String message;

  @ManyToOne
  @LazyCollection(LazyCollectionOption.FALSE)
  @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public AuthenticationLog(AUTHENTICATION_STATE state, String ip, String message, User user) {
    setState(state);
    setIp(ip);
    setMessage(message);
    setUser(user);
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof AuthenticationLog)) {
      return false;
    }

    AuthenticationLog other = (AuthenticationLog) object;
    return Objects.equals(this.getId(), other.getId());
  }

  @Override
  public int hashCode() {
    if (this.getId() == null) {
      return super.hashCode();
    }

    // @formatter:off
    return new HashCodeBuilder()
        .append(this.getId())
        .build();
    // @formatter:on
  }
}
