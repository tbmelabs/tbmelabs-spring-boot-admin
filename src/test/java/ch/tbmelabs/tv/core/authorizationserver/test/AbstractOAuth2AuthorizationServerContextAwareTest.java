package ch.tbmelabs.tv.core.authorizationserver.test;

import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;
import ch.tbmelabs.tv.core.authorizationserver.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

@AutoConfigureMockMvc
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({SpringApplicationProfileConstants.TEST, SpringApplicationProfileConstants.NO_REDIS,
  SpringApplicationProfileConstants.NO_MAIL})
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ServletTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
  DependencyInjectionTestExecutionListener.class, WithSecurityContextTestExecutionListener.class})
public abstract class AbstractOAuth2AuthorizationServerContextAwareTest {

}
