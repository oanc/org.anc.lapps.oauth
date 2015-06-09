package org.anc.lapps.oauth.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Keith Suderman
 */
@Entity
@Table(name = "authorities")
@IdClass(UserRoleKey.class)
public class UserRole
{
	@Id
	protected String username;
	@Id
	protected String authority;

	public UserRole()
	{

	}

	public UserRole(String username, String authority)
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
