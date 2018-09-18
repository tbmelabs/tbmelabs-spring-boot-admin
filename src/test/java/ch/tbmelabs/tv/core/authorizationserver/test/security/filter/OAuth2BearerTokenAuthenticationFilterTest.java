package ch.tbmelabs.tv.core.authorizationserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.security.filter.OAuth2BearerTokenAuthenticationFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.filter.GenericFilterBean;

public class OAuth2BearerTokenAuthenticationFilterTest {

  @Mock
  private TokenStore tokenStore;

  @Spy
  @InjectMocks
  private OAuth2BearerTokenAuthenticationFilter fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void oAuth2BearerTokenAuthenticationFilterShouldBeAnnotated() {
    assertThat(OAuth2BearerTokenAuthenticationFilter.class).hasAnnotation(Component.class);
  }

  @Test
  public void oAuth2BearerTokenAuthenticationFilterShouldExtendsGenericFilterBean() {
    assertThat(GenericFilterBean.class)
        .isAssignableFrom(OAuth2BearerTokenAuthenticationFilter.class);
  }

  @Test
  public void oAuth2BearerTokenAuthenticationConstructorShouldAcceptTokenStore() {
    assertThat(new OAuth2BearerTokenAuthenticationFilter(Mockito.mock(TokenStore.class)))
        .isNotNull();
  }

  @Test
  public void initBeanShouldInitializeBearerTokenExtractor() {
    assertThat(ReflectionTestUtils.getField(fixture, "bearerTokenExtractor")).isNull();

    fixture.initBean();

    assertThat(ReflectionTestUtils.getField(fixture, "bearerTokenExtractor")).isNotNull()
        .isInstanceOf(BearerTokenExtractor.class);
  }
}
