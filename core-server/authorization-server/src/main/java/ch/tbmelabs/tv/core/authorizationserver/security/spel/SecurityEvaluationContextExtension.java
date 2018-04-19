package ch.tbmelabs.tv.core.authorizationserver.security.spel;

import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityEvaluationContextExtension extends EvaluationContextExtensionSupport {
  @Override
  public String getExtensionId() {
    return "security";
  }

  @Override
  public SecurityExpressionRoot getRootObject() {
    return new SecurityExpressionRoot(SecurityContextHolder.getContext().getAuthentication()) {};
  }
}
