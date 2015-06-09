package org.anc.lapps.oauth.web;

/**
 * @author Keith Suderman
 */
public class ClientData
{
	protected String name;
	protected String clientId;
	protected String clientSecret;

	public ClientData()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getClientSecret()
	{
		return clientSecret;
	}

	public void setClientSecret(String clientSecret)
	{
		this.clientSecret = clientSecret;
	}
}
