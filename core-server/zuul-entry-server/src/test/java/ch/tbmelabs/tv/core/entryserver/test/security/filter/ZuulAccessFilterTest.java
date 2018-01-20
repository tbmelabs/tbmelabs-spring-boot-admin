package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ZuulAccessFilterTest extends AbstractZuulApplicationContextAware {
  @Test
  public void zuulAccessFilterShouldBeAnnotated() {
    assertThat(ZuulAccessFilter.class).hasAnnotation(Component.class);
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.DEV);
  }

  @Test
  public void zuulAccessFilterShouldNotOccurInProductiveEnvironments() {
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value())
        .contains(SpringApplicationProfile.DEV);
  }
}