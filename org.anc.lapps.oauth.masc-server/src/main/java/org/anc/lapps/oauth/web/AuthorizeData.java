package org.anc.lapps.oauth.web;

/**
 * @author Keith Suderman
 */
public class AuthorizeData
{
	protected String redirect;
	protected String id;
	protected String name;

	public AuthorizeData()
	{

	}

	public AuthorizeData(String redirect, String id, String name)
	{
		this.redirect = redirect;
		this.id = id;
		this.name = name;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
