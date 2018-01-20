package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@SpringBootApplication
@EnableAutoConfiguration
@Profile({ SpringApplicationProfile.TEST })
@ComponentScan(basePackages = { "ch.tbmelabs.tv.shared.centralizedloggingwithelkstack" })
public class TestApplication {
}