package ch.tbmelabs.tv.webapp.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = { "classpath:application.properties" })
public interface JunitSpringImpl {
}