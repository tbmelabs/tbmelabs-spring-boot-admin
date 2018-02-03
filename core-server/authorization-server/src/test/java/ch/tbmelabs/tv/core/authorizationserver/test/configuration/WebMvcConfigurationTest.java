package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import ch.tbmelabs.tv.core.authorizationserver.configuration.WebMvcConfiguration;

public class WebMvcConfigurationTest {
  @Mock
  private ResourceHandlerRegistry resourceHandlerRegistry;

  @Mock
  private WebMvcConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).viewResolver();
    doCallRealMethod().when(fixture).addResourceHandlers(resourceHandlerRegistry);
  }

  @Test
  public void webMVCConfigurationShouldBeAnnotated() {
    assertThat(WebMvcConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void internalResourceViewResolverBeanShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    assertThat(WebMvcConfiguration.class.getMethod("viewResolver", new Class<?>[] {}).getDeclaredAnnotation(Bean.class))
        .isNotNull();
  }

  @Test
  public void internalResourceViewResolverShouldReturnCorrectResolver() {
    assertThat(fixture.viewResolver()).hasFieldOrPropertyWithValue("prefix", "/").hasFieldOrPropertyWithValue("suffix",
        ".html");
  }
}