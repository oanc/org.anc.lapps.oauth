package org.anc.lapps.oauth.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Keith Suderman
 */
@Entity
@Table(name = "users")
public class User
{
	@Id
	protected String username;
	@Column
	protected String password;
	@Column
	protected boolean enabled;

	public User()
	{

	}

	public User(String username, String password, boolean enabled)
	{
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
