package org.anc.lapps.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Keith Suderman
 */
public class OAuthToken
{
	@JsonProperty("expires_in")
	private Long expires;
	@JsonProperty("access_token")
	private String access;
	@JsonProperty("refresh_token")
	private String refresh;

	public OAuthToken()
	{

	}

	public OAuthToken(Long expires, String access, String refresh)
	{
		this.expires = expires;
		this.access = access;
		this.refresh = refresh;
	}

	public Long getExpires()
	{
		return expires;
	}

	public void setExpires(Long expires)
	{
		this.expires = expires;
	}

	public String getAccess()
	{
		return access;
	}

	public void setAccess(String access)
	{
		this.access = access;
	}

	public String getRefresh()
	{
		return refresh;
	}

	public void setRefresh(String refresh)
	{
		this.refresh = refresh;
	}
}
