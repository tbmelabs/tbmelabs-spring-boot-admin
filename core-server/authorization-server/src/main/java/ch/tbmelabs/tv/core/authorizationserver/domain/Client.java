package ch.tbmelabs.tv.core.authorizationserver.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clients")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends NicelyDocumentedJDBCResource {

  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  public static final String REDIRECT_URI_SPLITTERATOR = ";";

  @Id
  @GenericGenerator(name = "pk_sequence",
      strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY,
      parameters = {@Parameter(name = "sequence_name", value = "clients_id_seq"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 36, min = 36)
  @Column(name = "client_id", columnDefinition = "bpchar(36)", unique = true)
  private String clientId;

  @Length(max = 36, min = 36)
  @Column(columnDefinition = "bpchar(36)")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String secret;

  @NotNull
  @Column(name = "secret_required")
  private Boolean isSecretRequired = true;

  @NotNull
  @Column(name = "auto_approve")
  private Boolean isAutoApprove = false;

  @NotNull
  @Column(name = "access_token_validity")
  private Integer accessTokenValiditySeconds;

  @NotNull
  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValiditySeconds;

  @Length(max = 256)
  private String redirectUri;

  @JsonManagedReference("client_has_grant_types")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "client")
  private Collection<ClientGrantTypeAssociation> grantTypes;

  @JsonManagedReference("client_has_authorities")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "client")
  private Collection<ClientAuthorityAssociation> grantedAuthorities;

  @JsonManagedReference("client_has_scopes")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "client")
  private Collection<ClientScopeAssociation> scopes;
}
