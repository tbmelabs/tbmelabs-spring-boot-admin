package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserProfileMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;

public class UserProfileMapperTest {
  @Mock
  private UserRoleAssociationCRUDRepository userRoleAssociationRepository;

  @Spy
  @InjectMocks
  private UserProfileMapper fixture;
}