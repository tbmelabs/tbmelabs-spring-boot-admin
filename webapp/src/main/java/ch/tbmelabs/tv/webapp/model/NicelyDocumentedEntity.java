package ch.tbmelabs.tv.webapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class NicelyDocumentedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @NotNull
  protected Date created;

  @NotNull
  @Column(name = "last_updated")
  protected Date lastUpdated;

  @PrePersist
  public void onCreate() {
    created = new Date();
    lastUpdated = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    lastUpdated = new Date();
  }
}