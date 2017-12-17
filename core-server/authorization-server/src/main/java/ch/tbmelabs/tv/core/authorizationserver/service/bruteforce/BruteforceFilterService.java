package ch.tbmelabs.tv.core.authorizationserver.service.bruteforce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.shared.domain.authentication.bruteforce.BlacklistedIp;

@Service
public class BruteforceFilterService {
  private static Map<String, Integer> failedLoginAttempts = new HashMap<>();

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  public static void resetFilter() {
    BruteforceFilterService.failedLoginAttempts.clear();
  }

  public static Map<String, Integer> getState() {
    return BruteforceFilterService.failedLoginAttempts;
  }

  public void authenticationFromIpFailed(String ip) {
    if (!BruteforceFilterService.failedLoginAttempts.containsKey(ip)) {
      BruteforceFilterService.failedLoginAttempts.put(ip, 0);
    }

    BruteforceFilterService.failedLoginAttempts.put(ip, BruteforceFilterService.failedLoginAttempts.get(ip) + 1);

    if (BruteforceFilterService.failedLoginAttempts.get(ip) >= maxLoginAttempts) {
      suspectBruteforceAttempt(ip);
    }
  }

  private void suspectBruteforceAttempt(String ip) {
    ipBlacklistRepository.save(new BlacklistedIp(ip));
  }

  public void authenticationFromIpSucceed(String ip) {
    BruteforceFilterService.failedLoginAttempts.remove(ip);
  }
}