package ch.tbmelabs.tv.shared.constants.spring;

public class SpringApplicationProfile {
  /**
   * Productive systems.
   */
  public static final String PROD = "prod";

  /**
   * Development profile with debug logs.
   */
  public static final String DEV = "dev";

  /**
   * Used while (maven) testing is active.
   */
  public static final String TEST = "test";

  /**
   * Can possibly be used to skip database depending tests. It is not
   * recommended to use it as it fakes test results and might lead the build to
   * have failures!
   */
  public static final String NO_DB = "nodb";

  /**
   * Activates logging via Logstash and ELK stack.
   */
  public static final String ELK = "elk";
}