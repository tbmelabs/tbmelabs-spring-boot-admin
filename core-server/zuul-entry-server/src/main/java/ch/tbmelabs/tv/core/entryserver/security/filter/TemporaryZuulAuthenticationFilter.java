package ch.tbmelabs.tv.core.entryserver.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ch.tbmelabs.tv.shared.domain.authentication.user.Role;
import ch.tbmelabs.tv.shared.domain.authentication.user.Role.DefaultRole;

@Component
public class TemporaryZuulAuthenticationFilter extends GenericFilterBean {
  private static final Logger LOGGER = LogManager.getLogger(TemporaryZuulAuthenticationFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext()
        .getAuthentication().getAuthorities().contains(new Role(DefaultRole.TMP_ZUUL_USER.toString()))) {
      LOGGER.info("Removing existing " + DefaultRole.TMP_ZUUL_USER + " authentication");

      SecurityContextHolder.getContext().setAuthentication(null);
    }

    chain.doFilter(request, response);
  }
}