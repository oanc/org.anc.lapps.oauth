package org.anc.lapps.oauth.web;

/**
 * @author Keith Suderman
 */
public class PasswordChangeData
{
	protected String username;
	protected String existing;
	protected String password1;
	protected String password2;

	public PasswordChangeData()
	{

	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getExisting()
	{
		return existing;
	}

	public void setExisting(String existing)
	{
		this.existing = existing;
	}

	public String getPassword1()
	{
		return password1;
	}

	public void setPassword1(String password1)
	{
		this.password1 = password1;
	}

	public String getPassword2()
	{
		return password2;
	}

	public void setPassword2(String password2)
	{
		this.password2 = password2;
	}

	// Fluent API methods.
	public PasswordChangeData username(String username)
	{
		this.username = username;
		return this;
	}
	public PasswordChangeData existing(String password)
	{
		this.existing = password;
		return this;
	}
	public PasswordChangeData password1(String password)
	{
		this.password1 = password;
		return this;
	}
	public PasswordChangeData password2(String password)
	{
		this.password2 = password;
		return this;
	}
}
