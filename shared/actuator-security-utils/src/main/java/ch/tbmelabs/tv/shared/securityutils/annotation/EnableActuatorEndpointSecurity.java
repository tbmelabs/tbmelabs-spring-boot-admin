package ch.tbmelabs.tv.shared.securityutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import ch.tbmelabs.tv.shared.securityutils.configuration.ActuatorEndpointSecurityConfiguration;
import ch.tbmelabs.tv.shared.securityutils.configuration.ApplicationProperties;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Import({ApplicationProperties.class, ActuatorEndpointSecurityConfiguration.class})
public @interface EnableActuatorEndpointSecurity {

}
