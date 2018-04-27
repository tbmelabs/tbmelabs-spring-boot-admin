package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mapstruct.Mapper;
import org.reflections.Reflections;
import ch.tbmelabs.tv.core.authorizationserver.Application;

public class MapperAnnotationTest {

  private static final Integer EXPECTED_MAPPER_COUND = 12;

  @Test
  public void packageShouldOnlyContainConfigurations() {
    new Reflections(Application.class.getPackage().getName() + ".domain.dto.mapper")
        .getSubTypesOf(Object.class)
        .forEach(mapper -> assertThat(mapper.getClass().getSimpleName()).contains("Mapper"));
  }

  @Test
  public void allConfigurationsShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain.dto.mapper")
        .getTypesAnnotatedWith(Mapper.class)).hasSize(EXPECTED_MAPPER_COUND);
  }
}
