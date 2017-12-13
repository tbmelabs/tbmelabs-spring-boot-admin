package ch.tbmelabs.core.servicediscovery.test.web;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAwareJunitTest;

public class WebSecurityOAuth2SSOForwardTest extends AbstractEurekaApplicationContextAwareJunitTest {
  @Test
  public void requestToRootURLShouldForwardToLoginEndpoint() throws Exception {
    // TODO: As soon as WebSecurity is properly initialized
  }
}