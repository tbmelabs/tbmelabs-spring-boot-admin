package ch.tbmelabs.tv.core.authorizationserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractAuditingEntity implements Serializable {

  @Transient
  public static final String SEQUENCE_GENERATOR_STRATEGY =
    "org.hibernate.id.enhanced.SequenceStyleGenerator";

  @Transient
  public static final String HASH_CODE_SEPARATOR = "/";

  @Transient
  private static final long serialVersionUID = 1L;

  @CreatedDate
  @Setter(AccessLevel.NONE)
  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  public Date created;

  @LastModifiedDate
  @Setter(AccessLevel.NONE)
  @Column(name = "last_updated")
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  public Date lastUpdated;
}
