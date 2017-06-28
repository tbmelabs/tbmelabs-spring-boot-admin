package ch.tbmelabs.tv.webapp.security.role;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecurityRole {
  private static final Logger LOGGER = LogManager.getLogger(SecurityRole.class);

  public static final String ROLE_GUEST = "GUEST";
  public static final String ROLE_USER = "USER";
  public static final String ROLE_CONTENT_ADMIN = "CONTENT_ADMIN";
  public static final String ROLE_SERVER_ADMIN = "SERVER_ADMIN";
  public static final String ROLE_APPLICATION_ADMIN = "GANDALF";

  private SecurityRole() {
    // Hidden constructor
  }

  public static Map<String, String> getAllRoles() {
    Map<String, String> roles = new HashMap<>();

    Arrays.asList(SecurityRole.class.getDeclaredFields()).stream().forEach(field -> {
      if (field.getName().contains("ROLE")) {
        try {
          roles.put(field.getName(), field.get(SecurityRole.class).toString());
        } catch (IllegalArgumentException | IllegalAccessException e) {
          LOGGER.error(e);
        }
      }
    });

    return roles;
  }
}