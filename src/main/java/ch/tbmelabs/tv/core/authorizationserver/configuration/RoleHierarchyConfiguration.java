package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import ch.tbmelabs.serverconstants.security.UserRoleEnum;

@Configuration
public class RoleHierarchyConfiguration {

  private static final List<String> ROLE_HIERARCHY = new ArrayList<>();

  static {
    ROLE_HIERARCHY.add(UserRoleEnum.GANDALF.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.SERVER_ADMIN.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.SERVER_SUPPORT.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.CONTENT_ADMIN.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.CONTENT_SUPPORT.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.PREMIUM_USER.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.USER.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.GUEST.getAuthority());
    ROLE_HIERARCHY.add(UserRoleEnum.ANONYMOUS.getAuthority());
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
