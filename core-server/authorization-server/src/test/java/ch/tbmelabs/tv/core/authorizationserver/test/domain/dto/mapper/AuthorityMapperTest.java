package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.AuthorityMapper;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityMapperTest {

  public static class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public AuthorityDTO toDto(Authority entity) {
      return new AuthorityDTO(entity.getName());
    }

    @Override
    public Authority toEntity(AuthorityDTO dto) {
      return new Authority(dto.getName());
    }

    @Override
    public List<AuthorityDTO> toDtos(List<Authority> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Authority> toEntities(List<AuthorityDTO> dtos) {
      return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
  }
}
