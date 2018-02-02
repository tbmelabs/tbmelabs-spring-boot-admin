package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ZuulAccessFilterTest {
  @Test
  public void zuulAccessFilterShouldBeAnnotated() {
    assertThat(ZuulAccessFilter.class).hasAnnotation(Component.class);
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.DEV);
  }
}