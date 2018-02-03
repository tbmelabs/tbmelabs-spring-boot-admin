package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ZuulAccessFilterTest {
  @Mock
  private ZuulAccessFilter fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).filterType();
    doCallRealMethod().when(fixture).filterOrder();
    doCallRealMethod().when(fixture).shouldFilter();
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
  }
}