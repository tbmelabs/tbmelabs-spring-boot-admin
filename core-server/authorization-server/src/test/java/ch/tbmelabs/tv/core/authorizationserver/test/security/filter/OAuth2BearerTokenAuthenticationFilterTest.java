package ch.tbmelabs.tv.core.authorizationserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.filter.GenericFilterBean;

import ch.tbmelabs.tv.core.authorizationserver.security.filter.OAuth2BearerTokenAuthenticationFilter;

public class OAuth2BearerTokenAuthenticationFilterTest {
  @Mock
  private OAuth2BearerTokenAuthenticationFilter fixture;

  @Before
  public void beforeTestSetUp() throws IOException, ServletException {
    initMocks(this);

    doCallRealMethod().when(fixture).initBean();
    doCallRealMethod().when(fixture).doFilter(Mockito.any(ServletRequest.class), Mockito.any(ServletResponse.class),
        Mockito.any(FilterChain.class));
  }

  @Test
  public void oAuth2BearerTokenAuthenticationFilterShouldBeAnnotated() {
    assertThat(OAuth2BearerTokenAuthenticationFilter.class).hasAnnotation(Component.class);
  }

  @Test
  public void oAuth2BearerTokenAuthenticationFilterShouldExtendsGenericFilterBean() {
    assertThat(GenericFilterBean.class).isAssignableFrom(OAuth2BearerTokenAuthenticationFilter.class);
  }

  @Test
  public void initBeanShouldInitializeBearerTokenExtractor() {
    assertThat(ReflectionTestUtils.getField(fixture, "bearerTokenExtractor")).isNull();

    fixture.initBean();

    assertThat(ReflectionTestUtils.getField(fixture, "bearerTokenExtractor")).isNotNull()
        .isOfAnyClassIn(BearerTokenExtractor.class);
  }
}