package ch.tbmelabs.springbootadmin.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.test.util.ReflectionTestUtils;
import ch.tbmelabs.springbootadmin.Application;
import ch.tbmelabs.springbootadmin.ServletInitializer;

public class ServletInitializerTest {

  private ServletInitializer fixture;

  @Before
  public void beforeTestSetup() {
    fixture = new ServletInitializer();
  }

  @Test
  public void extendsSpringBootServletInitializer() {
    assertThat(SpringBootServletInitializer.class).isAssignableFrom(ServletInitializer.class);
  }

  @Test
  public void configureShouldAddApplicationSourceToApplicationBuilder() {
    SpringApplicationBuilder builder = Mockito.mock(SpringApplicationBuilder.class);

    ReflectionTestUtils.invokeMethod(fixture, "configure", builder);

    verify(builder, times(1)).sources(Application.class);
  }
}
