package ch.tbmelabs.tv.webapp.security.config;

import javax.persistence.Table;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.Role;
import ch.tbmelabs.tv.webapp.security.login.LoginErrorListener;
import ch.tbmelabs.tv.webapp.security.login.LoginSuccessListener;
import ch.tbmelabs.tv.webapp.security.role.SecurityRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private LoginSuccessListener authSuccessHandler;

  @Autowired
  private LoginErrorListener authErrorHandler;

  @Autowired
  private DataSource dataSource;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(Account.PASSWORD_ENCODER)
        .usersByUsernameQuery("SELECT username,password,NOT is_blocked FROM "
            + Account.class.getAnnotation(Table.class).name() + " WHERE username=?")
        .authoritiesByUsernameQuery(
            "SELECT u.username,a.role_name FROM " + Account.class.getAnnotation(Table.class).name() + " AS u LEFT JOIN "
                + Role.class.getAnnotation(Table.class).name() + " AS a ON u.al_id=a.id WHERE u.username=?");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Ignore preflight requests
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");

    // For development purposes only!
    // .and().debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Basic security config
    http.cors().disable().csrf().disable().jee().disable().x509().disable()

        // Configuration for the graphical user interface
        // Permit all requests to the default pages, redirect to
        // login-page if further interaction requested
        .authorizeRequests().antMatchers("/*", "/login/**", "/register/**", "/public/**").permitAll().anyRequest()
        .authenticated()

        // Admin section
        .antMatchers("/admin/**").permitAll().anyRequest()
        .hasAnyAuthority(SecurityRole.ROLE_CONTENT_ADMIN, SecurityRole.ROLE_SERVER_ADMIN,
            SecurityRole.ROLE_APPLICATION_ADMIN)

        // Login configuration
        .and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
        .successHandler(authSuccessHandler).failureHandler(authErrorHandler)

        // Logout configuration
        .and().logout().logoutUrl("/logout").permitAll();
  }
}