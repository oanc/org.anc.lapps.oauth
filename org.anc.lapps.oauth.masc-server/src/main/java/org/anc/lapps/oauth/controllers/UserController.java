package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.MainMenu;
import org.anc.lapps.oauth.data.UserDatabase;
import org.anc.lapps.oauth.service.UserService;
import org.anc.lapps.oauth.web.Menu;
import org.anc.lapps.oauth.web.PasswordChangeData;
import org.anc.lapps.oauth.web.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Controller
public class UserController
{
//	@Autowired
//	protected DataSource userDataSource;

//	@Autowired
//	protected UserDatabase userDatabase;

	@Autowired
	protected UserService userService;

	protected List<Menu> navbar;

	public UserController()
	{
		navbar = new ArrayList<>(3);
		navbar.add(new Menu("list", "/user/list"));
		navbar.add(new Menu("add", "/user/add"));
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String list(Model model)
	{
		List<UserData> users = userService.findAll();
		if (users == null)
		{
			model.addAttribute("error", "There was a problem fetching the user list from the database.");
			users = new ArrayList<>();
		}
		model.addAttribute("users", users);
		selectMenu(model);
		return "user/list";
	}

	@RequestMapping(value = "/user/submit", method = RequestMethod.POST)
	public String submit(@ModelAttribute UserData user, Model model)
	{
		model.addAttribute("message", "Adding user " + user.getUsername());
//		selectMenu(model);
//		return "user/list";
		return list(model);
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String add(Model model)
	{
		model.addAttribute("data", new UserData());
		selectMenu(model);
		return "user/add";
	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("user") String username, Model model)
	{
		model.addAttribute("message", "Will delete user " + username);
//		selectMenu(model);
//		return "user/list";
		return list(model);
	}

	@RequestMapping(value="/user/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("user") String username, Model model)
	{
		if (username == null)
		{
			model.addAttribute("error", "No username provided.");
			return list(model);
		}
		UserData dummy = new UserData();
		dummy.setUsername("Dummy");
		dummy.setPassword1("DummyPassword");
		dummy.setEmail("dummy@example.com");
		model.addAttribute("data", dummy);

		selectMenu(model);
		return "user/edit";
	}

	@RequestMapping(value = "/user/password", method = RequestMethod.GET)
	public String password(@RequestParam("user") String username, Model model)
	{
		if (username == null)
		{
			model.addAttribute("error", "No username provided");
			return list(model);
		}
		PasswordChangeData data = new PasswordChangeData();
		data.setUsername(username);
		model.addAttribute("data", data);
		selectMenu(model);
		return "user/password";
	}

	private void selectMenu(Model model)
	{
		model.addAttribute("menu", MainMenu.selectUserMenu());
		model.addAttribute("navbar", navbar);
	}
}
