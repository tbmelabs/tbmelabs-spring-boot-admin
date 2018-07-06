package ch.tbmelabs.tv.core.entryserver.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZuulAccessFilter extends ZuulFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZuulAccessFilter.class);

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

    LOGGER.info("REQUEST  :: < {} {}:{}", request.getScheme(), request.getLocalAddr(),
        request.getLocalPort());
    LOGGER.info("REQUEST  :: < {} {} {}", request.getMethod(), request.getRequestURI(),
        request.getProtocol());
    LOGGER.info("RESPONSE :: > HTTP:{}", response.getStatus());

    return null;
  }
}
