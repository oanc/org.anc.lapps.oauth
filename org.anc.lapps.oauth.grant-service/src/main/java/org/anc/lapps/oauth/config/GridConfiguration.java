package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Keith Suderman
 */
public class GridConfiguration implements Configuration
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

	@Override
	public String getServiceUrl()
	{
		return serviceUrl;
	}

	@Override
	public String getRedirectUrl()
	{
		return redirectUrl;
	}

	@Override
	public String getTokenRedirectUrl()
	{
		return tokenRedirectUrl;
	}

	@Override
	public String getResourceUrl()
	{
		return resourceUrl;
	}

	@Override
	public String getResourceTokenUrl()
	{
		return resourceTokenUrl;
	}

	@Override
	public String getResourceAuthorizeUrl()
	{
		return resourceAuthorizeUrl;
	}
}
