package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.SleuthSamplerConfiguration;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ LogstashAppenderConfiguration.class, SleuthSamplerConfiguration.class })
public @interface EnableCentralizedLogging {
}