package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatasourceConfiguration {

  private static final Logger LOGGER = LogManager.getLogger(DatasourceConfiguration.class);

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  public HikariDataSource dataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  @Profile({SpringApplicationProfile.NO_REDIS})
  @ConfigurationProperties("tokenstore.datasource")
  public HikariDataSource jdbcTokenStoreDatasource() {
    LOGGER.warn("Profile \"" + SpringApplicationProfile.NO_REDIS
        + "\" is active: tokenstore will be of " + HikariDataSource.class);

    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
}
