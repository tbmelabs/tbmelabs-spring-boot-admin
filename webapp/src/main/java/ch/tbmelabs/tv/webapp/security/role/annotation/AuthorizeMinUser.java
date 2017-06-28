package ch.tbmelabs.tv.webapp.security.role.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@AuthorizeUser
@AuthorizeContentAdmin
@AuthorizeServerAdmin
@AuthorizeApplicationAdmin
public @interface AuthorizeMinUser {
}