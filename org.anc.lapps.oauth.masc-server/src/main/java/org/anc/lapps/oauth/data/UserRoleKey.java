package org.anc.lapps.oauth.data;

import java.io.Serializable;

/**
 * @author Keith Suderman
 */
public class UserRoleKey implements Serializable
{
	protected String username;
	protected String authority;

	public UserRoleKey()
	{

	}

	public UserRoleKey(String username, String authority)
	{
		this.username = username;
		this.authority = authority;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}
}
