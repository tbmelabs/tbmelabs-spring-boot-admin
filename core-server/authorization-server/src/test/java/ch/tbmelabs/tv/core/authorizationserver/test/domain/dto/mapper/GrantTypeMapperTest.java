package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.GrantTypeMapper;
import java.util.List;
import java.util.stream.Collectors;

public class GrantTypeMapperTest {

  public static class GrantTypeMapperImpl implements GrantTypeMapper {

    @Override
    public GrantTypeDTO toDto(GrantType entity) {
      return new GrantTypeDTO(entity.getName());
    }

    @Override
    public GrantType toEntity(GrantTypeDTO dto) {
      return new GrantType(dto.getName());
    }

    @Override
    public List<GrantTypeDTO> toDtos(List<GrantType> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GrantType> toEntities(List<GrantTypeDTO> dtos) {
      return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
  }
}
