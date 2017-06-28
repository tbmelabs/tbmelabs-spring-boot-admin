package ch.tbmelabs.tv.webapp.security.token;

import java.util.UUID;

public class SecurityTokenGenerator {
  private SecurityTokenGenerator() {
    // Hidden constructor
  }

  public static UUID getSecurityToken() {
    return UUID.randomUUID();
  }

  public static String getSecurityTokenString() {
    return UUID.randomUUID().toString();
  }
}