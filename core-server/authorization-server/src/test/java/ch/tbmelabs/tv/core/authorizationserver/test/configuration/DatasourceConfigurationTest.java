package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import ch.tbmelabs.tv.core.authorizationserver.configuration.DatasourceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class DatasourceConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String PRIMARY_DATASOURCE_NAME = "primaryDataSource";
  private static final String PRIMARY_DATASOURCE_PREFIX = "spring.datasource";
  private static final String JDBC_DATASOURCE_NAME = "jdbcTokenStoreDatasource";
  private static final String JDBC_TOKEN_STORE_DATASOURCE_PREFIX = "tokenstore.datasource";

  @Test
  public void dataSourceConfigurationShouldBeAnnotated() {
    assertThat(DatasourceConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", DatasourceConfiguration.class,
        Configuration.class);
  }

  @Test
  public void springDataSourceShouldBeAnnotatedAsPrimaryBean() throws NoSuchMethodException, SecurityException {
    Method datasourceConfiguration = DatasourceConfiguration.class.getDeclaredMethod(PRIMARY_DATASOURCE_NAME,
        new Class[] {});

    assertThat(datasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(Primary.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(ConfigurationProperties.class).prefix())
        .isEqualTo(PRIMARY_DATASOURCE_PREFIX)
        .withFailMessage("The %s should use the default \"%s\"!", datasourceConfiguration, PRIMARY_DATASOURCE_PREFIX);
  }

  @Test
  public void jdbcTokenStoreShouldNotOccurInProductiveEnvironment() throws NoSuchMethodException, SecurityException {
    Method jdbcTokenStoreDatasourceConfiguration = DatasourceConfiguration.class.getDeclaredMethod(JDBC_DATASOURCE_NAME,
        new Class[] {});

    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Profile.class).value())
        .containsAll(Arrays.asList(new String[] { SpringApplicationProfile.DEV, SpringApplicationProfile.TEST }))
        .doesNotContain(SpringApplicationProfile.PROD)
        .withFailMessage("The @%s annotation should not allow the %s to occur in productive environments!",
            Profile.class, JdbcTokenStore.class);
    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(ConfigurationProperties.class).prefix())
        .isEqualTo(JDBC_TOKEN_STORE_DATASOURCE_PREFIX).withFailMessage("The %s should use the default \"%s\"!",
            jdbcTokenStoreDatasourceConfiguration, JDBC_TOKEN_STORE_DATASOURCE_PREFIX);
  }
}