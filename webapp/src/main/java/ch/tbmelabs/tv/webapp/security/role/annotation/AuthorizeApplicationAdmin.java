package ch.tbmelabs.tv.webapp.security.role.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

import ch.tbmelabs.tv.webapp.security.role.SecurityRole;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('" + SecurityRole.ROLE_APPLICATION_ADMIN + "')")
public @interface AuthorizeApplicationAdmin {
}