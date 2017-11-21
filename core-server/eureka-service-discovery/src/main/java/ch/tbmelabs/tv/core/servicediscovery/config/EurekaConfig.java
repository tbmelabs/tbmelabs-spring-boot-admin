package ch.tbmelabs.tv.core.servicediscovery.config;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableEurekaServer
@PropertySource({ "classpath:eureka/server.properties" })
public class EurekaConfig {
}