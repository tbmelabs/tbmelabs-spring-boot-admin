package ch.tbmelabs.tv.core.entryserver;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@EnableZuulProxy
@SpringCloudApplication
public class Application extends SpringBootServletInitializer {

  private static final Class<Application> APPLICATION_SOURCE_CLASS = Application.class;

  private Environment environment;

  public Application(Environment environment) {
    this.environment = environment;
  }

  public static void main(String[] args) {
    SpringApplication.run(APPLICATION_SOURCE_CLASS, args);
  }

  @PostConstruct
  public void initBean() {
    if (environment.acceptsProfiles(SpringApplicationProfile.PROD)
        && environment.acceptsProfiles(SpringApplicationProfile.DEV)) {
      throw new IllegalArgumentException(
          "Do not attempt to run an application in productive and development environment at the same time!");
    }
  }
}
