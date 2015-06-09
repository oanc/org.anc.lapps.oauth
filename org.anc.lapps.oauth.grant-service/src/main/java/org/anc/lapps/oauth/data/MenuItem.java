package org.anc.lapps.oauth.data;

/**
 * @author Keith Suderman
 */
public class MenuItem
{
	protected String label;
	protected String href;

	public MenuItem()
	{

	}

	public MenuItem(String label, String href)
	{
		this.label = label;
		this.href = href;
	}

	public String getLabel()
	{
		return label;
	}

	public String getHref()
	{
		return href;
	}
}

