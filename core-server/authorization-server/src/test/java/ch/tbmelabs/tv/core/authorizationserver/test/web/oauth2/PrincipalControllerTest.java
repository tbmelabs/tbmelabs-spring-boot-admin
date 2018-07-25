package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.web.oauth2.PrincipalController;
import java.lang.reflect.Method;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class PrincipalControllerTest {

  @Test
  public void principalControllerShouldBeAnnotated() {
    assertThat(PrincipalController.class).hasAnnotation(RestController.class);
  }

  @Test
  public void getPrincipalShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method fixture = PrincipalController.class.getDeclaredMethod("getPrincipal");
    assertThat(fixture.getDeclaredAnnotation(GetMapping.class).value()).containsExactly("/me",
        "/user");
  }

  @Test
  public void getProfileShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method fixture = PrincipalController.class.getDeclaredMethod("getProfile");
    assertThat(fixture.getDeclaredAnnotation(GetMapping.class).value()).containsExactly("/profile");
  }
}
