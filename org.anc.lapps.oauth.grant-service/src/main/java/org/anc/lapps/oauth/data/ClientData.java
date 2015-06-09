package org.anc.lapps.oauth.data;


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

//
///**
// * @author Keith Suderman
// */
//public class ClientData
//{
//	private String id;
//	private String secret;
//
//	public ClientData()
//	{
//
//	}
//
//	public ClientData(String id, String secret)
//	{
//		this.id = id;
//		this.secret = secret;
//	}
//
//	public String getId()
//	{
//		return id;
//	}
//
//	public void setId(String id)
//	{
//		this.id = id;
//	}
//
//	public String getSecret()
//	{
//		return secret;
//	}
//
//	public void setSecret(String secret)
//	{
//		this.secret = secret;
//	}
//}
