package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "email_confirmation_tokens")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailConfirmationToken extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "email_confirmation_tokens_id_seq"),
      @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(min = 60, max = 60)
  @Column(columnDefinition = "bpchar(60)", unique = true)
  private String tokenString;

  @NotNull
  private Date expirationDate;

  @JsonBackReference("user_has_email_confirmation_token")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToOne(cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public EmailConfirmationToken(String tokenString, User user) {
    this.tokenString = tokenString;
    this.user = user;
  }

  @Override
  @PrePersist
  public void onCreate() {
    super.onCreate();

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 1);
    this.expirationDate = calendar.getTime();
  }
}