package org.anc.lapps.oauth.web;

import org.anc.lapps.oauth.data.ClientData;
import org.anc.lapps.oauth.data.Menu;
import org.anc.lapps.oauth.database.Client;
import org.anc.lapps.oauth.database.ClientDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Keith Suderman
 */
@Controller
public class ClientController
{
	@Autowired
	protected ClientDatabase clientDatabase;

	protected MainMenu menu;
	public ClientController()
	{
		menu = new MainMenu();
	}

	@RequestMapping(value="/client/register", method= RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String clientRegister(Model model)
	{
		model.addAttribute("clientData", new ClientData());
		selectMenu(model, menu.selectClientMenu());
		return "client/register";
	}

	@RequestMapping(value = "/client/submit", method = RequestMethod.POST)
	public String clientSubmit(@ModelAttribute ClientData clientData, Model model)
	{
		String clientId = clientData.getName(); //.replaceAll(" ", "").toLowerCase();
//		String clientId = String.format("org.lappsgrid.oauth.client.%s", name);
		Client client = clientDatabase.findByClientId(clientId);
		if (client != null)
		{
			model.addAttribute("error", "A client with that URI already exists. Please try another.");
			model.addAttribute("clientData", clientData);
//			selectMenu(model, registerMenu);
			selectMenu(model, menu.selectClientMenu());
			return "client/register";
		}
		String clientSecret = UUID.randomUUID().toString();
		client = new Client(clientId, clientSecret);
		clientDatabase.save(client);
		clientData.setClientId(clientId);
		clientData.setClientSecret(clientSecret);
		selectMenu(model, menu.selectClientMenu());
		model.addAttribute("clientData", clientData);
		model.addAttribute("message", "New client registered.");
		model.addAttribute("clients", clientDatabase.findAll());
		return "client/list";
	}

	@RequestMapping(value = "/client/delete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String clientDelete(@RequestParam(value = "id", required = false)String idString, Model model)
	{
		if (idString != null)
		{
			Long id;
			try {
				id = Long.parseLong(idString);
			}
			catch (NumberFormatException e)
			{
				model.addAttribute("error", "Invalid client id.");
				return clientList(model);
			}
			Client client = clientDatabase.findById(id);
			if (client == null)
			{
				model.addAttribute("error", "Client not found.");
			}
			else
			{
				clientDatabase.delete(client);
				model.addAttribute("message", "Client deleted.");
			}
		}
		return clientList(model);
	}

	@RequestMapping(value="/client/list",method=RequestMethod.GET, produces = "text/html")
	public String clientList(Model model)
	{
//		selectMenu(model, registerMenu);
//		List<Menu> menu = new ArrayList<>(3);
//		Menu clientMenu = new Menu("Client", "/client/list");
//		clientMenu.active();
//		menu.add(clientMenu);
//		menu.add(new Menu("Code", "/code/list"));
//		menu.add(new Menu("Token", "/token/list"));
//		model.addAttribute("menu", menu);
		model.addAttribute("clients", clientDatabase.findAll());
		selectMenu(model, this.menu.selectClientMenu());
		return "client/list";
	}

	private void selectMenu(Model model, List<Menu> menus)
	{
		model.addAttribute("menus", menus);
	}
}
