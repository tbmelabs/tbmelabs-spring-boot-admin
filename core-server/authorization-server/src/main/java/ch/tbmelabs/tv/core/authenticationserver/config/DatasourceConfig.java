package ch.tbmelabs.tv.core.authenticationserver.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatasourceConfig {
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Profile("dev")
  @ConfigurationProperties(prefix = "tokenstore.datasource")
  public DataSource jdbcTokenStoreDatasource() {
    return DataSourceBuilder.create().build();
  }
}