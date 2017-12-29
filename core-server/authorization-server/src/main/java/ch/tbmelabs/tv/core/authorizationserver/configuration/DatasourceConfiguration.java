package ch.tbmelabs.tv.core.authorizationserver.configuration;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
public class DatasourceConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(DatasourceConfiguration.class);

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Profile({ SpringApplicationProfile.DEV, SpringApplicationProfile.TEST })
  @ConfigurationProperties(prefix = "tokenstore.datasource")
  public DataSource jdbcTokenStoreDatasource() {
    LOGGER.warn("Either profile \"" + SpringApplicationProfile.DEV + "\" or \"" + SpringApplicationProfile.TEST
        + "\" is active: tokenstore will use a PostgreSQL database");

    return DataSourceBuilder.create().build();
  }
}