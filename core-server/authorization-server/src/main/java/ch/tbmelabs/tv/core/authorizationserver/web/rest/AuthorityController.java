package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.AuthorityMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/authorities"})
@PreAuthorize("hasAuthority('" + UserAuthority.SERVER_ADMIN + "')")
public class AuthorityController {

  @Autowired
  private AuthorityCRUDRepository authorityRepository;

  @Autowired
  private AuthorityMapper authorityMapper;

  @GetMapping
  public Page<AuthorityDTO> getAllAuthorities(Pageable pageable) {
    return authorityRepository.findAll(pageable).map(authorityMapper::toDto);
  }
}
