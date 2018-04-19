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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

  @Autowired
  private Environment environment;

  @Pointcut("within(ch.tbmelabs.tv.core.authorizationserver.repository..*) "
      + "|| within(ch.tbmelabs.tv.core.authorizationserver.service..*)"
      + " || within(ch.tbmelabs.tv.core.authorizationserver.web..*)")
  public void loggingPointcut() {
    // Implementations are in the advices.
  }

  @AfterThrowing(pointcut = "loggingPointcut()", throwing = "throwable")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
    if (environment.acceptsProfiles(SpringApplicationProfile.DEV)) {
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
