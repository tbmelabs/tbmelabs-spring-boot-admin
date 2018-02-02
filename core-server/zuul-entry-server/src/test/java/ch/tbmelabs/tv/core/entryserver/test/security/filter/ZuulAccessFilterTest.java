package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ZuulAccessFilterTest {
  private static ZuulAccessFilter fixture;

  @BeforeClass
  public static void beforeClassSetUp() {
    fixture = new ZuulAccessFilter();
  }

  @Test
  public void zuulAccessFilterShouldBeAnnotated() {
    assertThat(ZuulAccessFilter.class).hasAnnotation(Component.class);
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.DEV);
  }

  @Test
  public void filterTypeShouldBePost() {
    assertThat(fixture.filterType()).isEqualTo("post");
  }

  @Test
  public void filterOrderShouldBeOne() {
    assertThat(fixture.filterOrder()).isEqualTo(1);
  }

  @Test
  public void filterShouldBeActive() {
    assertThat(fixture.shouldFilter()).isTrue();
    assertThat(fixture.isFilterDisabled()).isFalse();
  }
}