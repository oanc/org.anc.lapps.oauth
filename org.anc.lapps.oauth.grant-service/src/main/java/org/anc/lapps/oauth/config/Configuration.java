package org.anc.lapps.oauth.config;

/**
 * @author Keith Suderman
 */
public interface Configuration
{
	String getServiceUrl();

	String getRedirectUrl();

	String getTokenRedirectUrl();

	String getResourceUrl();

	String getResourceTokenUrl();

	String getResourceAuthorizeUrl();
}
