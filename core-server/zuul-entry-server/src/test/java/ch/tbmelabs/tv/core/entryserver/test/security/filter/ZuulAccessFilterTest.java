package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;

public class ZuulAccessFilterTest extends AbstractZuulApplicationContextAwareJunitTest {
  @Test
  public void zuulAccessFilterShouldBeAnnotated() {
    assertThat(ZuulAccessFilter.class).hasAnnotation(Component.class).withFailMessage("Annotate "
        + ZuulAccessFilter.class + " with " + Component.class + " to make it scannable for the spring application!");

    assertThat(ZuulAccessFilter.class).hasAnnotation(Profile.class).withFailMessage("Annotate " + ZuulAccessFilter.class
        + " with " + Profile.class + " because we dont want this to filter public requests!");
  }

  @Test
  public void zuulAccessFilterShouldNotOccurInProductiveEnvironments() {
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value()).contains("dev").withFailMessage(
        "Assing the value \"dev\" to " + Profile.class + " to keep this filter away from the productive environment!");
  }
}