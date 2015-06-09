package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.MainMenu;
import org.anc.lapps.oauth.database.AuthorizationCode;
import org.anc.lapps.oauth.database.AuthorizationCodeDatabase;
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
public class CodeController
{
	@Autowired
	protected AuthorizationCodeDatabase database;

	protected List<Menu> navbar;

	public CodeController()
	{
		navbar = new ArrayList<>(2);
		navbar.add(new Menu("list", "/code/list"));
	}

	@RequestMapping(value = "/code/list", method = RequestMethod.GET)
	public String list(Model model)
	{
		selectMenu(model);
		model.addAttribute("codes", database.findAll());
		return "code/list";
	}

	@RequestMapping(value = "/code/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", required = false) String idValue, Model model)
	{
		if (idValue == null)
		{
			model.addAttribute("error", "No id value provided.");
			return list(model);
		}
		Long id = -1L;
		try {
			id = Long.parseLong(idValue);
		}
		catch (NumberFormatException e)
		{
			model.addAttribute("error", "Invalid id value.");
			return list(model);
		}
		AuthorizationCode code = database.findById(id);
		if (code == null)
		{
			model.addAttribute("error", "No such authorization code.");
			return list(model);
		}
		database.delete(code);
		model.addAttribute("message", "Authorization code revoked.");
		return list(model);
	}


	protected void selectMenu(Model model)
	{
		model.addAttribute("menu", MainMenu.selectCodeMenu());
		model.addAttribute("navbar", navbar);
	}
}
