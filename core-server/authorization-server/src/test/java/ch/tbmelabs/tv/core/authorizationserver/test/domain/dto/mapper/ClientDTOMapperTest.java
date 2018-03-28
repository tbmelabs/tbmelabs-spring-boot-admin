package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientDTOMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientAuthorityAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientGrantTypeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;

public class ClientDTOMapperTest {
  @Mock
  private ClientGrantTypeAssociationCRUDRepository clientGrantTypeRepository;

  @Mock
  private ClientAuthorityAssociationCRUDRepository clientAuthorityRepository;

  @Mock
  private ClientScopeAssociationCRUDRepository clientScopeRepository;

  @Spy
  @InjectMocks
  private ClientDTOMapper fixture;
}