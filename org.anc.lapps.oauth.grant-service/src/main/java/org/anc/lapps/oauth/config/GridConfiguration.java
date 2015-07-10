package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Keith Suderman
 */
@EnableConfigurationProperties
public class GridConfiguration extends AbstractConfiguration
{
	@Value("${grid.service.url}")
	private String serviceUrl;
	@Value("${grid.service.redirect.url}")
	private String redirectUrl;
	@Value("${grid.service.token.url}")
	private String tokenRedirectUrl;

	@Value("${resource.url}")
	private String resourceUrl;
	@Value("${resource.token.url}")
	private String resourceTokenUrl;
	@Value("${resource.authorize.url}")
	private String resourceAuthorizeUrl;

	public GridConfiguration()
	{

	}

//	@Override
//	public String getServiceUrl()
//	{
//		return serviceUrl;
//	}
//
//	@Override
//	public String getRedirectUrl()
//	{
//		return redirectUrl;
//	}
//
//	@Override
//	public String getTokenRedirectUrl()
//	{
//		return tokenRedirectUrl;
//	}
//
//	@Override
//	public String getResourceUrl()
//	{
//		return resourceUrl;
//	}
//
//	@Override
//	public String getResourceTokenUrl()
//	{
//		return resourceTokenUrl;
//	}
//
//	@Override
//	public String getResourceAuthorizeUrl()
//	{
//		return resourceAuthorizeUrl;
//	}
}
