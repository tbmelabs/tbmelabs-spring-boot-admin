package ch.tbmelabs.tv.core.entryserver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import ch.tbmelabs.tv.core.entryserver.Application;

@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles({ "test" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource({ "classpath:application-test.properties" })
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ ServletTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public abstract class AbstractZuulApplicationContextAwareJunitTest {
  @Test
  public void zuulApplicationContextLoads() {
  }
}