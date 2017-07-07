package ch.tbmelabs.tv.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authentication_log")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class AuthenticationLog extends NicelyDocumentedEntity {
  @NotEmpty
  @Column(name = "status", columnDefinition = "VARCHAR(32)")
  private String status;

  @NotEmpty
  @Column(name = "ip", columnDefinition = "CHAR(45)")
  private String ip;

  @Column(name = "message", columnDefinition = "VARCHAR(255)")
  private String message;

  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE }, optional = true)
  @JoinColumn(name = "a_id")
  protected Account account;
}