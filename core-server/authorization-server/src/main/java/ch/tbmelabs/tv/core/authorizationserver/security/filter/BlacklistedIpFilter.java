package ch.tbmelabs.tv.core.authorizationserver.security.filter;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class BlacklistedIpFilter extends GenericFilterBean {

  private IPBlacklistCRUDRepository ipBlacklistRepository;

  public BlacklistedIpFilter(IPBlacklistCRUDRepository ipBlacklistCRUDRepository) {
    this.ipBlacklistRepository = ipBlacklistCRUDRepository;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (ipBlacklistRepository.findOneByStartIpLessThanAndEndIpGreaterThan(request.getRemoteAddr(),
        request.getRemoteAddr()).isPresent()) {
      ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value());
    }

    chain.doFilter(request, response);
  }
}
