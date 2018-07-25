package ch.tbmelabs.tv.core.authorizationserver.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.AuthorityMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthorityCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.web.rest.AuthorityController;
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

public class AuthorityControllerTest {

  @Mock
  private AuthorityCRUDRepository mockAuthorityRepository;

  @Mock
  private AuthorityMapper mockAuthorityMapper;

  @Spy
  @InjectMocks
  private AuthorityController fixture;

  private Authority testAuthority;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    testAuthority = new Authority(RandomStringUtils.random(11));

    doReturn(new PageImpl<>(Collections.singletonList(testAuthority))).when(mockAuthorityRepository)
        .findAll(ArgumentMatchers.any(Pageable.class));

    doAnswer((Answer<AuthorityDTO>) arg0 -> {
      AuthorityDTO dto = new AuthorityDTO();
      dto.setName(((Authority) arg0.getArgument(0)).getName());
      return dto;
    }).when(mockAuthorityMapper).toDto(Mockito.any(Authority.class));
  }

  @Test
  public void authorityControllerShouldBeAnnotated() {
    assertThat(AuthorityController.class).hasAnnotation(RestController.class)
        .hasAnnotation(RequestMapping.class).hasAnnotation(PreAuthorize.class);
    assertThat(AuthorityController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("${spring.data.rest.base-path}/authorities");
    assertThat(AuthorityController.class.getDeclaredAnnotation(PreAuthorize.class).value())
        .isEqualTo("hasAuthority('" + UserAuthority.SERVER_ADMIN + "')");
  }

  @Test
  public void getAllAuthoritiesShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method method = AuthorityController.class.getDeclaredMethod("getAllAuthorities",
        Pageable.class);
    assertThat(method.getDeclaredAnnotation(GetMapping.class).value()).isEmpty();
  }

  @Test
  public void getAllAuthoritiesShouldReturnAllAuthorities() {
    assertThat(fixture.getAllAuthorities(Mockito.mock(Pageable.class)).getContent()).hasSize(1)
        .containsExactly(mockAuthorityMapper.toDto(testAuthority));
    verify(mockAuthorityRepository, times(1)).findAll(ArgumentMatchers.any(Pageable.class));
  }
}
