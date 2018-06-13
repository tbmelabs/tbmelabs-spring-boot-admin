package ch.tbmelabs.tv.core.authorizationserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "blacklisted_ips")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlacklistedIp extends AuditingEntity {

  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = AuditingEntity.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "blacklisted_ips_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 45)
  private String startIp;

  @NotEmpty
  @Length(max = 45)
  private String endIp;

  public BlacklistedIp(String startIp, String endIp) {
    setStartIp(startIp);
    setEndIp(endIp);
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof BlacklistedIp)) {
      return false;
    }

    BlacklistedIp other = (BlacklistedIp) object;
    return Objects.equals(this.getId(), other.getId())
        && Objects.equals(this.getStartIp(), other.getStartIp())
        && Objects.equals(this.getEndIp(), other.getEndIp());
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
