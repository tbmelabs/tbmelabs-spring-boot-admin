package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "ch.tbmelabs.tv.shared.centralizedloggingwithelkstack" })
public class TestApplication {
}