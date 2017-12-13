package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.core.entryserver.configuration.SecurityConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;

public class SecurityConfigurationTest extends AbstractZuulApplicationContextAwareJunitTest {
  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class)
        .withFailMessage("Annotate " + SecurityConfiguration.class + " with " + Configuration.class
            + " to make it scannable for the spring application!");

    assertThat(SecurityConfiguration.class).hasAnnotation(EnableWebSecurity.class)
        .withFailMessage("Annotate " + SecurityConfiguration.class + " with " + EnableWebSecurity.class
            + " to affect the " + WebSecurityConfigurerAdapter.class + "!");

    assertThat(SecurityConfiguration.class).hasAnnotation(EnableGlobalMethodSecurity.class)
        .withFailMessage("Annotate " + SecurityConfiguration.class + " with " + EnableGlobalMethodSecurity.class
            + " enable global spring method security!");

    assertThat(SecurityConfiguration.class).hasAnnotation(Order.class).withFailMessage("Annotate "
        + SecurityConfiguration.class + " with " + Order.class + " to get along with " + EnableZuulProxy.class + "!");
  }
}