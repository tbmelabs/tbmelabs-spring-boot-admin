package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Aspect
public class LoggingAspect {
  private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

  private final Environment environment;

  public LoggingAspect(Environment environment) {
    this.environment = environment;
  }

  @Pointcut("within(ch.tbmelabs.tv.core.authorizationserver.repository..*) "
      + "|| within(ch.tbmelabs.tv.core.authorizationserver.service..*)"
      + " || within(ch.tbmelabs.tv.core.authorizationserver.web..*)")
  public void loggingPointcut() {
    // Implementations are in the advices.
  }

  @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    if (environment.acceptsProfiles(SpringApplicationProfile.DEV)) {
      LOGGER.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
          e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);

    } else {
      LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }
  }

  @Around("loggingPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), result);
      }

      return result;
    } catch (IllegalArgumentException e) {
      LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

      throw e;
    }
  }
}