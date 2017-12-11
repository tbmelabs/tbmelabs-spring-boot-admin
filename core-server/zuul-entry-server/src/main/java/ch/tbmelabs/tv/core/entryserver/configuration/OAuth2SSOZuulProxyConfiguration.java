package ch.tbmelabs.tv.core.entryserver.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZuulProxy
@EnableOAuth2Sso
public class OAuth2SSOZuulProxyConfiguration {
}