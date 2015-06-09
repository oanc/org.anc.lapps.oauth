package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.MainMenu;
import org.anc.lapps.oauth.database.*;
import org.anc.lapps.oauth.web.Menu;
import org.anc.lapps.oauth.web.ClientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
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
	@Autowired
	protected ClientTokenDatabase clientTokenDatabase;
	@Autowired
	protected SequenceDatabase sequenceDatabase;

	private List<Menu> navbar;
	private Menu listMenu;
	private Menu registerMenu;

	public ClientController()
	{
		navbar = new ArrayList<>();
		listMenu = new Menu("list", "/client/list");
		listMenu.setIcon("list");
		navbar.add(listMenu);
		registerMenu = new Menu("register", "/client/register");
		registerMenu.setIcon("cog");
		navbar.add(registerMenu);

	}

	@RequestMapping(value="/client/register", method= RequestMethod.GET, produces = MediaType.TEXT_HTML)
	public String register(Model model)
	{
		selectMenu(model);
		model.addAttribute("clientData", new ClientData());
		return "client/register";
	}
	
	@RequestMapping(value = "/client/submit", method = RequestMethod.POST)
	public String submit(@ModelAttribute ClientData clientData, Model model)
	{
		String name = clientData.getName(); //.replaceAll(" ", "").toLowerCase();
		Client client = clientDatabase.findByName(name);
		if (client != null)
		{
			model.addAttribute("error", "A client with that name already exists. Please try another.");
			model.addAttribute("clientData", clientData);
			selectMenu(model);
			return "client/register";
		}
//		long n = this.getNextId();

		String clientId = "org.anc.masc.client";
		String clientSecret = UUID.randomUUID().toString();
		client = new Client(name, clientId, clientSecret);
		clientDatabase.save(client);
		clientData.setClientId(clientId + client.getId());
		clientData.setClientSecret(clientSecret);
		selectMenu(model);
		model.addAttribute("client", clientData);
		model.addAttribute("message", "New client registered.");
		return "client/view";
	}

	@RequestMapping(value = "/client/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", required = false)String idValue, Model model)
	{
		if (idValue == null)
		{
			return error(model, "No id value provided.");
		}
		Long id = -1L;
		try
		{
			id = Long.parseLong(idValue);
		}
		catch (NumberFormatException e)
		{
			return error(model, "Invalid id value.");
		}
		Client client = clientDatabase.findById(id);
		if (client == null)
		{
			return error(model, "No such client.");
		}
		clientTokenDatabase.deleteByClient(client.getId());
		clientDatabase.delete(client);
		model.addAttribute("message", "Client deleted.");
		return list(model);
	}

	@RequestMapping(value="/client/list",method=RequestMethod.GET, produces = "text/html")
	public String list(Model model)
	{
		model.addAttribute("clients", clientDatabase.findAll());
		selectMenu(model);
		return "client/list";
	}

	private void  selectMenu(Model model)
	{
		model.addAttribute("menu", MainMenu.selectClientMenu());
		model.addAttribute("navbar", navbar);
	}

	private String error(Model model, String message)
	{
		model.addAttribute("error", message);
		return list(model);
	}

	@Transactional
	synchronized private long _getNextId()
	{
		long n = 0;
		Sequence sequence = sequenceDatabase.findByName("client");
		if (sequence == null)
		{
			sequence = new Sequence("client", 1L);
			sequenceDatabase.save(sequence);
		}
		else
		{
			n = sequence.increment();
			sequenceDatabase.save(sequence);
		}
		return n;
	}
}
