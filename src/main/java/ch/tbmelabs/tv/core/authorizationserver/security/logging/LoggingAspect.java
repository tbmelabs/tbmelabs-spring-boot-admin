package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  private Environment environment;

  public LoggingAspect(Environment environment) {
    this.environment = environment;
  }

  @Pointcut("within(ch.tbmelabs.tv.core.authorizationserver.domain.repository..*) "
      + "|| within(ch.tbmelabs.tv.core.authorizationserver.service..*)"
      + " || within(ch.tbmelabs.tv.core.authorizationserver.web..*)")
  public void loggingPointcut() {
    // Implementations are in the advices.
  }

  @AfterThrowing(pointcut = "loggingPointcut()", throwing = "throwable")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
    if (environment.acceptsProfiles(SpringApplicationProfileEnum.DEV.getName())) {
      LOGGER.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
          throwable.getCause() != null ? throwable.getCause() : "NULL", throwable.getMessage(),
          throwable);
    } else {
      LOGGER.error("Exception in {}.{}() with cause = {}",
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
          throwable.getCause() != null ? throwable.getCause() : "NULL");
    }
  }

  @Around("loggingPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    LOGGER.debug("Enter: {}.{}() with argument[s] = {}",
        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
    try {
      Object result = joinPoint.proceed();

      LOGGER.debug("Exit: {}.{}() with result = {}",
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
          result);

      return result;
    } catch (IllegalArgumentException e) {
      LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

      throw e;
    }
  }
}
