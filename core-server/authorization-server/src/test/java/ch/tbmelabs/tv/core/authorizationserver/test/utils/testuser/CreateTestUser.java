package ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateTestUser {
  String username();

  String email();

  String password();

  String confirmation();

  boolean isEnabled() default true;

  boolean isBlocked() default false;

  String[] authorities() default {};
}