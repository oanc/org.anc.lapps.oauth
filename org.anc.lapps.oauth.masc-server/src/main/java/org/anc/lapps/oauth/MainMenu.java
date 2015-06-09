package org.anc.lapps.oauth;

import org.anc.lapps.oauth.web.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
public class MainMenu
{
	protected static Menu clientMenu;
	protected static Menu codeMenu;
	protected static Menu tokenMenu;
	protected static Menu userMenu;
	protected static List<Menu> menu;

	static {
		clientMenu = new Menu("Client", "/client/list");
		codeMenu = new Menu("Code", "/code/list");
		tokenMenu = new Menu("Token", "/token/list");
		userMenu = new Menu("User", "/user/list");
		menu = new ArrayList<>(3);
		menu.add(clientMenu);
		menu.add(codeMenu);
		menu.add(tokenMenu);
		menu.add(userMenu);
		selectClientMenu();
	}

	private MainMenu()
	{
	}

	public static List<Menu> selectClientMenu()
	{
		select(clientMenu);
		return menu;
	}

	public static List<Menu> selectCodeMenu()
	{
		select(codeMenu);
		return menu;
	}

	public static List<Menu> selectTokenMenu()
	{
		select(tokenMenu);
		return menu;
	}

	public static List<Menu> selectUserMenu()
	{
		select(userMenu);
		return menu;
	}
	
	private static void select(Menu selected)
	{
		for (Menu item : menu)
		{
			item.inactive();
		}
		selected.active();
	}
}
