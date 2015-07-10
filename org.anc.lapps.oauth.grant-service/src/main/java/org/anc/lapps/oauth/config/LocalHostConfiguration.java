package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Keith Suderman
 */
@EnableConfigurationProperties
public class LocalHostConfiguration extends AbstractConfiguration
{
	@Value("${localhost.service.url}")
	private String serviceUrl;
	@Value("${localhost.service.redirect.url}")
	private String redirectUrl;
	@Value("${localhost.service.token.url}")
	private String tokenRedirectUrl;
	@Value("${localhost.resource.url}")
	private String resourceUrl;
	@Value("${localhost.resource.token.url}")
	private String resourceTokenUrl;
	@Value("${localhost.resource.authorize.url}")
	private String resourceAuthorizeUrl;

	public LocalHostConfiguration()
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
