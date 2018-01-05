package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.production;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Component
@Profile({ SpringApplicationProfile.PROD })
public class ProductiveEnvironmentWithoutCentralizedLoggingCheck {
  private static final Logger LOGGER = LogManager.getLogger(ProductiveEnvironmentWithoutCentralizedLoggingCheck.class);

  @Autowired
  private Environment environment;

  @PostConstruct
  public void initBean() {
    if (!Arrays.stream(environment.getActiveProfiles()).filter(env -> env.equals(SpringApplicationProfile.ELK))
        .findAny().isPresent()) {
      // @formatter:off
      LOGGER.warn(
            "\n\n"
          + "!!! ------------------------------------------------------- !!!\n"
          + "!!! ------------------------------------------------------- !!!\n"
          + "!!! -------- Found active profile " + SpringApplicationProfile.PROD + " without " + SpringApplicationProfile.ELK + " -------- !!!\n"
          + "!!! Consider monitoring your application with the ELK stack !!!\n"
          + "!!! ------------------------------------------------------- !!!\n"
          + "!!! ------------------------------------------------------- !!!\n\n"
          );
      // @formatter:on
    }
  }
}