package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Keith Suderman
 */
public class AbstractConfiguration implements Configuration
{
	private String serviceUrl;
	private String redirectUrl;
	private String tokenRedirectUrl;
	private String resourceUrl;
	private String resourceTokenUrl;
	private String resourceAuthorizeUrl;

	public AbstractConfiguration()
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
