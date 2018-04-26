package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ScopeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ScopeMapper;
import java.util.List;
import java.util.stream.Collectors;

public class ScopeMapperTest {

  public static class ScopeMapperImpl implements ScopeMapper {

    @Override
    public ScopeDTO toDto(Scope entity) {
      return new ScopeDTO(entity.getName());
    }

    @Override
    public Scope toEntity(ScopeDTO dto) {
      return new Scope(dto.getName());
    }

    @Override
    public List<ScopeDTO> toDtos(List<Scope> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Scope> toEntities(List<ScopeDTO> dtos) {
      return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
  }
}
