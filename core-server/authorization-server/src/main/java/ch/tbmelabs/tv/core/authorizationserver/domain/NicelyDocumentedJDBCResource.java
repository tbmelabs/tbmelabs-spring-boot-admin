package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@MappedSuperclass
public class NicelyDocumentedJDBCResource implements Serializable {

  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  public static final String SEQUENCE_GENERATOR_STRATEGY =
      "org.hibernate.id.enhanced.SequenceStyleGenerator";

  @NotNull
  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  public Date created;

  @NotNull
  @Setter(AccessLevel.NONE)
  @Column(name = "last_updated")
  @Temporal(TemporalType.TIMESTAMP)
  public Date lastUpdated;

  @PrePersist
  public void onCreate() {
    this.created = new Date();
    this.lastUpdated = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    this.lastUpdated = new Date();
  }
}
