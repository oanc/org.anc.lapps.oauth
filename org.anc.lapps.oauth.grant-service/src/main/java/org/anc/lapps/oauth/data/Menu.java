package org.anc.lapps.oauth.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Keith Suderman
 */
public class Menu
{
	protected String label;
	protected String href;

	protected List<MenuItem> items;
	protected boolean active;
	protected String icon;

	public Menu()
	{
		items = new ArrayList<>();
		active = false;
	}

	public Menu(String label)
	{
		this(label, (String) null);
	}

	public Menu(String label, String href)
	{
		this();
		this.label = label;
		this.href = href;
	}

	public Menu(String label, List<MenuItem> items)
	{
		this(label);
		this.items.addAll(items);
	}

	public Menu(String label, MenuItem[] items)
	{
		this(label);
		for (MenuItem item : items)
		{
			this.items.add(item);
		}
	}

	public boolean isActive() { return active; }
	public void active() { this.active = true; }
	public void inactive() { this.active = false; }
	public String getLabel() { return label ;}
	public List<MenuItem> getItems() { return items; }
	public String getIcon() { return icon; }
	public void setIcon(String icon) { this.icon = icon; }

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public Iterator<MenuItem> iterator()
	{
		return items.iterator();
	}

	public Menu add(String label, String href)
	{
		items.add(new MenuItem(label, href));
		return this;
	}

	public Menu add(MenuItem item)
	{
		items.add(item);
		return this;
	}
}
