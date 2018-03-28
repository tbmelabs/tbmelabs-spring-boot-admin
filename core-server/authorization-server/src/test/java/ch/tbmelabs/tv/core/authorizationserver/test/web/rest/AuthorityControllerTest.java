package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.web.rest.AuthorityController;

public class AuthorityControllerTest {
  @Mock
  private AuthorityCRUDRepository mockAuthorityRepository;

  @Spy
  @InjectMocks
  private AuthorityController fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(Mockito.mock(Page.class)).when(mockAuthorityRepository).findAll(Mockito.any(Pageable.class));
  }

  @Test
  public void authorityControllerShouldBeAnnotated() {
    assertThat(AuthorityController.class).hasAnnotation(RestController.class).hasAnnotation(RequestMapping.class);
    assertThat(AuthorityController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("${spring.data.rest.base-path}/authorities");
  }

  @Test
  public void authorityControllerShouldHavePublicConstructor() {
    assertThat(new AuthorityController()).isNotNull();
  }

  @Test
  public void getAllAuthoritiesShouldReturnAllAuthorities() throws NoSuchMethodException, SecurityException {
    Method method = AuthorityController.class.getDeclaredMethod("getAllAuthorities", new Class<?>[] { Pageable.class });
    assertThat(method.getDeclaredAnnotation(GetMapping.class).value()).isEmpty();

    assertThat(fixture.getAllAuthorities(Mockito.mock(Pageable.class)))
        .isEqualTo(mockAuthorityRepository.findAll(Mockito.mock(Pageable.class)));
  }
}