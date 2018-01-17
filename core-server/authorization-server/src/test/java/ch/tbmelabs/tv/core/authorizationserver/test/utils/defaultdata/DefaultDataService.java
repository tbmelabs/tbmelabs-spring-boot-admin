package ch.tbmelabs.tv.core.authorizationserver.test.utils.defaultdata;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.GrantTypeCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ScopeCRUDRepository;
import ch.tbmelabs.tv.shared.constants.oauth2.ClientGrantType;
import ch.tbmelabs.tv.shared.constants.oauth2.ClientScope;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Service
@Profile({ SpringApplicationProfile.TEST })
public class DefaultDataService {
  private static final Logger LOGGER = LogManager.getLogger(DefaultDataService.class);

  private static final String LOG_MESSAGE_PREFIX = "Adding default values for entitiy ";

  @Autowired
  private ScopeCRUDRepository scopeRepository;

  @Autowired
  private GrantTypeCRUDRepository grantTypeRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  private DefaultDataService() {
    // Hidden constructor
  }

  @PostConstruct
  public void initBean() {
    addDefaultClientScopes();
    addDefaultClientGrantTypes();
    addDefaultSecurityRoles();
  }

  private void addDefaultClientScopes() {
    LOGGER.info(LOG_MESSAGE_PREFIX + ClientScope.class);

    scopeRepository.save(new Scope(ClientScope.READ));
    scopeRepository.save(new Scope(ClientScope.WRITE));
    scopeRepository.save(new Scope(ClientScope.TRUST));
  }

  private void addDefaultClientGrantTypes() {
    LOGGER.info(LOG_MESSAGE_PREFIX + ClientGrantType.class);

    grantTypeRepository.save(new GrantType(ClientGrantType.AUTHORIZATION_CODE));
    grantTypeRepository.save(new GrantType(ClientGrantType.REFRESH_TOKEN));
    grantTypeRepository.save(new GrantType(ClientGrantType.IMPLICIT));
    grantTypeRepository.save(new GrantType(ClientGrantType.PASSWORD));
  }

  private void addDefaultSecurityRoles() {
    LOGGER.info(LOG_MESSAGE_PREFIX + SecurityRole.class);

    roleRepository.save(new Role(SecurityRole.ANONYMOUS));
    roleRepository.save(new Role(SecurityRole.GUEST));
    roleRepository.save(new Role(SecurityRole.USER));
    roleRepository.save(new Role(SecurityRole.PREMIUM_USER));
    roleRepository.save(new Role(SecurityRole.CONTENT_SUPPORT));
    roleRepository.save(new Role(SecurityRole.CONTENT_ADMIN));
    roleRepository.save(new Role(SecurityRole.SERVER_SUPPORT));
    roleRepository.save(new Role(SecurityRole.SERVER_ADMIN));
    roleRepository.save(new Role(SecurityRole.GANDALF));
  }
}