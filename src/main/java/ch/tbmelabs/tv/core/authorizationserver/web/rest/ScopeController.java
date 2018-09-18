package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.serverconstants.security.UserRoleConstants;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ScopeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ScopeMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ScopeCRUDRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/scopes"})
@PreAuthorize("hasAuthority('" + UserRoleConstants.SERVER_ADMIN + "')")
public class ScopeController {

  private ScopeCRUDRepository scopeRepository;

  private ScopeMapper scopeMapper;

  public ScopeController(ScopeCRUDRepository scopeCRUDRepository, ScopeMapper scopeMapper) {
    this.scopeRepository = scopeCRUDRepository;
    this.scopeMapper = scopeMapper;
  }

  @GetMapping
  public Page<ScopeDTO> getAllScopes(Pageable pageable) {
    return scopeRepository.findAll(pageable).map(scopeMapper::toDto);
  }
}
