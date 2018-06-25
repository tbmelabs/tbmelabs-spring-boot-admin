package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/");
    resolver.setSuffix(".html");
    return resolver;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/*").addResourceLocations("/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // TODO: How to deny access to anonymous urls for authenticated users?
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/signup").setViewName("signup");

    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }
}
