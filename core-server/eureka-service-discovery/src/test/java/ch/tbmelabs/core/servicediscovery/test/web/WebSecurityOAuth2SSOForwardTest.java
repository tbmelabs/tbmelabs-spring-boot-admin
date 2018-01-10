package ch.tbmelabs.core.servicediscovery.test.web;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;

public class WebSecurityOAuth2SSOForwardTest extends AbstractEurekaApplicationContextAware {
  @Test
  public void requestToRootURLShouldForwardToLoginEndpoint() throws Exception {
    // TODO: As soon as WebSecurity is properly initialized
  }
}