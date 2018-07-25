package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.shared.constants.security.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleHierarchyConfiguration {

  private static final List<String> ROLE_HIERARCHY = new ArrayList<>();

  static {
    ROLE_HIERARCHY.add(UserRole.GANDALF);
    ROLE_HIERARCHY.add(UserRole.SERVER_ADMIN);
    ROLE_HIERARCHY.add(UserRole.SERVER_SUPPORT);
    ROLE_HIERARCHY.add(UserRole.CONTENT_ADMIN);
    ROLE_HIERARCHY.add(UserRole.CONTENT_SUPPORT);
    ROLE_HIERARCHY.add(UserRole.PREMIUM_USER);
    ROLE_HIERARCHY.add(UserRole.USER);
    ROLE_HIERARCHY.add(UserRole.GUEST);
    ROLE_HIERARCHY.add(UserRole.ANONYMOUS);
  }

  private String getRoleHierarchy() {
    StringBuilder builder = new StringBuilder();

    builder.append(ROLE_HIERARCHY.get(0)).append(" > ").append(ROLE_HIERARCHY.get(1));

    IntStream.range(2, ROLE_HIERARCHY.size()).forEach(index -> builder.append(" AND ")
        .append(ROLE_HIERARCHY.get(index - 1)).append(" > ").append(ROLE_HIERARCHY.get(index)));

    return builder.toString();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy(getRoleHierarchy());
    return roleHierarchy;
  }
}
