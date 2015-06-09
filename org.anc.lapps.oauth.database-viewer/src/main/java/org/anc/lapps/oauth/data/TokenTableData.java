package org.anc.lapps.oauth.data;

/**
 * @author Keith Suderman
 */
public class TokenTableData
{
	long id;
	String token;
	String client;

	public TokenTableData(long id, String token, String client)
	{
		this.id = id;
		this.token = token;
		this.client = client;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getClient()
	{
		return client;
	}

	public void setClient(String client)
	{
		this.client = client;
	}
}
