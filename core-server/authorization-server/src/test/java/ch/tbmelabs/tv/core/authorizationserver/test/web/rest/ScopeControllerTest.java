package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ScopeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ScopeMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ScopeCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.web.rest.ScopeController;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import java.lang.reflect.Method;
import java.util.Collections;
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

public class ScopeControllerTest {

  @Mock
  private ScopeCRUDRepository mockScopeRepository;

  @Mock
  private ScopeMapper mockScopeMapper;

  @Spy
  @InjectMocks
  private ScopeController fixture;

  private Scope testScope;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    testScope = new Scope("TEST_SCOPE");

    doReturn(new PageImpl<>(Collections.singletonList(testScope))).when(mockScopeRepository)
        .findAll(ArgumentMatchers.any(Pageable.class));

    doAnswer((Answer<ScopeDTO>) arg0 -> {
      ScopeDTO dto = new ScopeDTO();
      dto.setName(((Scope) arg0.getArgument(0)).getName());
      return dto;
    }).when(mockScopeMapper).toDto(Mockito.any(Scope.class));
  }

  @Test
  public void scopeControllerShouldBeAnnotated() {
    assertThat(ScopeController.class).hasAnnotation(RestController.class)
        .hasAnnotation(RequestMapping.class).hasAnnotation(PreAuthorize.class);
    assertThat(ScopeController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("${spring.data.rest.base-path}/scopes");
    assertThat(ScopeController.class.getDeclaredAnnotation(PreAuthorize.class).value())
        .isEqualTo("hasAuthority('" + UserAuthority.SERVER_ADMIN + "')");
  }

  @Test
  public void scopeControllerShouldHavePublicConstructor() {
    assertThat(new ScopeController()).isNotNull();
  }

  @Test
  public void getAllScopesShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method =
        ScopeController.class.getDeclaredMethod("getAllScopes", new Class<?>[]{Pageable.class});
    assertThat(method.getDeclaredAnnotation(GetMapping.class).value()).isEmpty();
  }

  @Test
  public void getAllScopesShouldReturnAllAuthorities() {
    assertThat(fixture.getAllScopes(Mockito.mock(Pageable.class)).getContent()).hasSize(1)
        .containsExactly(mockScopeMapper.toDto(testScope));
    verify(mockScopeRepository, times(1)).findAll(ArgumentMatchers.any(Pageable.class));
  }
}
