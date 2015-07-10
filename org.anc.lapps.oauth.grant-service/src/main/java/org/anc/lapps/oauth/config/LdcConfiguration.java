package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Keith Suderman
 */
@EnableConfigurationProperties
public class LdcConfiguration extends AbstractConfiguration
{
	@Value("${grid.service.url}")
	private String serviceUrl;
	@Value("${grid.service.redirect.url}")
	private String redirectUrl;
	@Value("${grid.service.token.url}")
	private String tokenRedirectUrl;

	@Value("${ldc.resource.url}")
	private String resourceUrl;
	@Value("${ldc.resource.token.url}")
	private String resourceTokenUrl;
	@Value("${ldc.resource.authorize.url}")
	private String resourceAuthorizeUrl;

	public LdcConfiguration()
	{

	}
}
