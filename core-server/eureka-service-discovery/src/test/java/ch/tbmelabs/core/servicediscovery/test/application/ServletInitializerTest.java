package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.test.util.ReflectionTestUtils;
import ch.tbmelabs.tv.core.servicediscovery.Application;
import ch.tbmelabs.tv.core.servicediscovery.ServletInitalizer;

@RunWith(MockitoJUnitRunner.class)
public class ServletInitializerTest {

  @Spy
  private ServletInitalizer fixture;

  @Test
  public void applicationExtendsSpringBootServletInitializer() {
    assertThat(SpringBootServletInitializer.class).isAssignableFrom(ServletInitalizer.class);
  }

  @Test
  public void configureShouldAddApplicationSourceToApplicationBuilder() {
    SpringApplicationBuilder builder = Mockito.mock(SpringApplicationBuilder.class);

    ReflectionTestUtils.invokeMethod(fixture, "configure", builder);

    verify(builder, times(1)).sources(Application.class);
  }
}
