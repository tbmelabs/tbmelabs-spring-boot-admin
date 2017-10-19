package ch.tbmelabs.tv.authenticationserver.security.bruteforcing;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.authenticationserver.resource.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.resource.authentication.bruteforcing.BlacklistedIp;

@Component
public class BruteforceFilter {
  private static Map<String, Integer> failedLoginAttempts = new HashMap<>();

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  public void authenticationFromIpFailed(String ip) {
    if (!BruteforceFilter.failedLoginAttempts.containsKey(ip)) {
      BruteforceFilter.failedLoginAttempts.put(ip, 0);
    }

    BruteforceFilter.failedLoginAttempts.put(ip, BruteforceFilter.failedLoginAttempts.get(ip) + 1);

    if (BruteforceFilter.failedLoginAttempts.get(ip) >= maxLoginAttempts) {
      suspectBruteforceAttempt(ip);
    }
  }

  private void suspectBruteforceAttempt(String ip) {
    ipBlacklistRepository.save(new BlacklistedIp(ip));
  }

  public void authenticationFromIpSucceed(String ip) {
    BruteforceFilter.failedLoginAttempts.remove(ip);
  }
}