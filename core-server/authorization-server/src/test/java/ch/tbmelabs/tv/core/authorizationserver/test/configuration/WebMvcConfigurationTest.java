package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import ch.tbmelabs.tv.core.authorizationserver.configuration.WebMvcConfiguration;

public class WebMvcConfigurationTest {
  @Mock
  private ResourceHandlerRegistry resourceHandlerRegistryFixture;

  @Mock
  private ViewControllerRegistry viewControllerRegistryFixture;

  @Mock
  private WebMvcConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).viewResolver();
    doCallRealMethod().when(fixture).addResourceHandlers(resourceHandlerRegistryFixture);
    doCallRealMethod().when(fixture).addViewControllers(viewControllerRegistryFixture);
  }

  @Test
  public void webMVCConfigurationShouldBeAnnotated() {
    assertThat(WebMvcConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void webMVCConfigurationShouldHavePublicConstructor() {
    assertThat(new WebMvcConfiguration()).isNotNull();
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

  @Test
  @SuppressWarnings("unchecked")
  public void resourceHandlerRegistryShouldBeConfiguredCorrectly() {
    fixture.addResourceHandlers(resourceHandlerRegistryFixture);

    List<ResourceHandlerRegistration> registrations = (List<ResourceHandlerRegistration>) ReflectionTestUtils
        .getField(resourceHandlerRegistryFixture, "registrations");

    assertThat(registrations).isNotNull().hasSize(1);
    assertThat((String[]) ReflectionTestUtils.getField(registrations.get(0), "pathPatterns")).isNotNull().hasSize(1)
        .containsExactly("/**");
    assertThat((ArrayList<ServletContextResource>) ReflectionTestUtils.getField(registrations.get(0), "locations"))
        .isNotNull().hasSize(1);
    assertThat(
        ((ArrayList<ServletContextResource>) ReflectionTestUtils.getField(registrations.get(0), "locations")).get(0))
            .hasFieldOrPropertyWithValue("path", "/");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void viewControllerRegistryShouldBeConfiguredCorrectly() {
    fixture.addViewControllers(viewControllerRegistryFixture);

    List<ViewControllerRegistration> registrations = (List<ViewControllerRegistration>) ReflectionTestUtils
        .getField(viewControllerRegistryFixture, "registrations");

    assertThat(registrations).isNotNull().hasSize(4);
    assertThat(viewControllerRegistryFixture).hasFieldOrPropertyWithValue("order", Ordered.HIGHEST_PRECEDENCE);

    registrations.forEach(view -> {
      switch ((String) ReflectionTestUtils.getField(view, "urlPath")) {
      case "/":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("index");
        break;
      case "/signin":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("signin");
        break;
      case "/signup":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("signup");
        break;
      case "/oauth/confirm_access":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("authorize");
        break;
      default:
        throw new IllegalArgumentException("Unexpected view!");
      }
    });
  }
}