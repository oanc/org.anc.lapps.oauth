package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.MainMenu;
import org.anc.lapps.oauth.database.ClientTokenDatabase;
import org.anc.lapps.oauth.database.Token;
import org.anc.lapps.oauth.database.TokenDatabase;
import org.anc.lapps.oauth.web.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Controller
public class TokenController
{
	@Autowired
	protected TokenDatabase database;
	@Autowired
	protected ClientTokenDatabase clientTokenDatabase;

	protected List<Menu> navbar;

	public TokenController()
	{
		navbar = new ArrayList<>(2);
		navbar.add(new Menu("list", "/token/list"));
	}

	@RequestMapping(value = "/token/list", method = RequestMethod.GET)
	public String list(Model model)
	{
		selectMenu(model);
		return "token/list";
	}

	@RequestMapping(value = "/token/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", required = false)String idValue, Model model)
	{
		if (idValue == null)
		{
			return error(model, "No token id provided.");
		}
		Long id = -1L;
		try
		{
			id = Long.parseLong(idValue);
		}
		catch (NumberFormatException e)
		{
			return error(model, "Invalid token id.");
		}
		Token token = database.findById(id);
		if (token == null)
		{
			return error(model, "No such token.");
		}
		clientTokenDatabase.deleteByToken(token.getId());
		database.delete(token);
		model.addAttribute("message", "Token revoked.");
		return list(model);
	}

	protected String error(Model model, String message)
	{
		model.addAttribute("error", message);
		return list(model);
	}

	protected void selectMenu(Model model)
	{
		model.addAttribute("menu", MainMenu.selectTokenMenu());
		model.addAttribute("navbar", navbar);
	}

}
