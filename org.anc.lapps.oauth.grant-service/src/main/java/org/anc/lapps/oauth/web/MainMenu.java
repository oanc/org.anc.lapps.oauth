package org.anc.lapps.oauth.web;

import org.anc.lapps.oauth.Spring;
import org.anc.lapps.oauth.data.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Component
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "menu")
public class MainMenu
{
	@Value("${menu.index}")
	protected String index; // = "/ldc-authorize/index";
	@Value("${menu.client.list}")
	protected String clientList; // = "/ldc-authorize/client/list";
	@Value("${menu.client.register}")
	protected String clientRegister; // = "/ldc-authorize/client/register";

	protected List<Menu> menus;
	protected Menu plannerMenu;
	protected Menu clientMenu;

	public MainMenu()
	{
		menus = new ArrayList<>(2);
		plannerMenu = new Menu("LDC");
		plannerMenu.add("Authorize", index);
		menus.add(plannerMenu);

		clientMenu = new Menu("Client");
//		clientMenu.add("List", clientList);
//		clientMenu.add("Register", clientRegister);
//		menus.add(clientMenu);
	}

	public List<Menu> selectPlannerMenu()
	{
		plannerMenu.active();
		clientMenu.inactive();
		return menus;
	}

	public List<Menu> selectClientMenu()
	{
		plannerMenu.inactive();
		clientMenu.active();
		return menus;
	}

	public List<Menu> getMenus()
	{
		return menus;
	}
}
