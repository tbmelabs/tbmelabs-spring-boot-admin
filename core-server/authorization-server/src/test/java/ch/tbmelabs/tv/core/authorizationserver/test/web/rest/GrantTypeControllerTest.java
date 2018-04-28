package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.GrantTypeMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.GrantTypeCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.web.rest.GrantTypeController;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import java.lang.reflect.Method;
import java.util.Collections;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class GrantTypeControllerTest {

  @Mock
  private GrantTypeCRUDRepository mockGrantTypeRepository;

  @Mock
  private GrantTypeMapper mockGrantTypeMapper;

  @Spy
  @InjectMocks
  private GrantTypeController fixture;

  private GrantType testGrantType;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    testGrantType = new GrantType(RandomStringUtils.random(11));

    doReturn(new PageImpl<>(Collections.singletonList(testGrantType))).when(mockGrantTypeRepository)
        .findAll(ArgumentMatchers.any(Pageable.class));

    doAnswer((Answer<GrantTypeDTO>) arg0 -> {
      GrantTypeDTO dto = new GrantTypeDTO();
      dto.setName(((GrantType) arg0.getArgument(0)).getName());
      return dto;
    }).when(mockGrantTypeMapper).toDto(Mockito.any(GrantType.class));
  }

  @Test
  public void grantTypeControllerShouldBeAnnotated() {
    assertThat(GrantTypeController.class).hasAnnotation(RestController.class)
        .hasAnnotation(RequestMapping.class).hasAnnotation(PreAuthorize.class);
    assertThat(GrantTypeController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("${spring.data.rest.base-path}/grant-types");
    assertThat(GrantTypeController.class.getDeclaredAnnotation(PreAuthorize.class).value())
        .isEqualTo("hasAuthority('" + UserAuthority.SERVER_ADMIN + "')");
  }

  @Test
  public void grantTypeControllerShouldHavePublicConstructor() {
    assertThat(new GrantTypeController()).isNotNull();
  }

  @Test
  public void getAllGrantTypesShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method = GrantTypeController.class.getDeclaredMethod("getAllGrantTypes",
        new Class<?>[]{Pageable.class});
    assertThat(method.getDeclaredAnnotation(GetMapping.class).value()).isEmpty();
  }

  @Test
  public void getAllGrantTypesShouldReturnAllAuthorities() {
    assertThat(fixture.getAllGrantTypes(Mockito.mock(Pageable.class)).getContent()).hasSize(1)
        .containsExactly(mockGrantTypeMapper.toDto(testGrantType));
    verify(mockGrantTypeRepository, times(1)).findAll(ArgumentMatchers.any(Pageable.class));
  }
}
