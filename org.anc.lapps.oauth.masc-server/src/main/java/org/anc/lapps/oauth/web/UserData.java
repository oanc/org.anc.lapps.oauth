package org.anc.lapps.oauth.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Keith Suderman
 */
public class UserData
{
	protected String username;
	protected String password1;
	protected String password2;
	protected String email;
	protected List<String> roles;
	protected String authorities;
	protected boolean editable;

	public UserData()
	{
		roles = new ArrayList<>();
		editable = true;
	}

	public void build()
	{
		StringBuilder buffer = new StringBuilder();
		Iterator<String> iterator = roles.iterator();
		if (iterator.hasNext())
		{
			buffer.append(iterator.next().replace("ROLE_", ""));
		}
		while (iterator.hasNext())
		{
			buffer.append(", ");
			buffer.append(iterator.next().replace("ROLE_", ""));
		}
		authorities = buffer.toString();
	}

	public void setUsername(String username)
	{
		this.username = username;
		if (username.equals("admin") || username.equals("anonymous"))
		{
			editable = false;
		}
	}

	public String getUsername()
	{
		return username;
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public List<String> getRoles() { return roles; }
	public void setRoles(List<String> roles) { this.roles = roles; }

	public void addRole(String role)
	{
		roles.add(role);
	}

	public void addRoles(List<String> roles)
	{
		roles.addAll(roles);
	}

	public String getAuthorities()
	{
		return authorities;
	}

	public boolean isEditable()
	{
		return editable;
	}
}
