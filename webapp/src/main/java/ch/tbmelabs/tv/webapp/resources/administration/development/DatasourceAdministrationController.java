package ch.tbmelabs.tv.webapp.resources.administration.development;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.tbmelabs.tv.webapp.security.role.annotation.AuthorizeMinServerAdmin;

@Profile("dev")
@Controller
@AuthorizeMinServerAdmin
public class DatasourceAdministrationController {
  private static final String SCRIPTS_RESOURCES_PATH = "db/scripts/";

  @Autowired
  private DataSource dataSource;

  private static Resource cleanDatasourceScript = new ClassPathResource(
      SCRIPTS_RESOURCES_PATH + "clean-datasource.sql");
  private static Resource addDefaultDataScript = new ClassPathResource(SCRIPTS_RESOURCES_PATH + "add-default-data.sql");

  @RequestMapping(value = { "/admin/datasource/reset-datasource" }, method = RequestMethod.GET)
  public void resetDatasource() {
    new ResourceDatabasePopulator(cleanDatasourceScript).execute(dataSource);
    new ResourceDatabasePopulator(addDefaultDataScript).execute(dataSource);
  }
}