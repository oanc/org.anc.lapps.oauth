package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.database.Client;
import org.anc.lapps.oauth.database.ClientDatabase;
import org.anc.lapps.oauth.web.AuthorizeData;
import org.anc.lapps.oauth.web.Menu;
import org.anc.lapps.oauth.web.StringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * @author Keith Suderman
 */
@Controller
public class MascController
{


	final static String NO_REDIRECT = "The service that forwarded you here did not provide a redirection " +
		              "URL.  Please use your browser's Back button to return to that site " +
		              "and report the problem to that site's maintainers.";

	final static String NO_CLIENT_ID = "The client that directed you here did not provide " +
			  "a client_id value.  Please use your browser's Back button to return to that site " +
			  "and report the problem to that site's maintainers.";

	final static String UNKNOWN_CLIENT = "The client that directed you here is not authorized " +
			  "to access MASC data. Please use your brower's back button to return to that" +
			  "site and report the problem to that site's maintainers.";

	@Autowired
	protected ClientDatabase clientDatabase;

	public MascController()
	{

	}

	@RequestMapping(value = "/masc", method = RequestMethod.GET)
	public String masc(@RequestParam(value = "redirect_uri", required = false)String uri,
							 @RequestParam(value="client_id", required = false) String clientId,
							 Model model)
	{
		if (uri == null)
		{
			model.addAttribute("error", "Unable to Redirect");
			model.addAttribute("message", NO_REDIRECT);
			return "error";
		}
		if (clientId == null)
		{
			model.addAttribute("error", "Unidentified Client");
			model.addAttribute("message", NO_CLIENT_ID);
			return "error";
		}
		Client client = clientDatabase.findByClientId(clientId);
		if (client == null)
		{
			model.addAttribute("error", "Unknown Client");
			model.addAttribute("message", UNKNOWN_CLIENT);
			return "error";
		}
		String name = client.getName();
		AuthorizeData data = new AuthorizeData(uri, clientId, name);
		model.addAttribute("navbar", new ArrayList<Menu>());
		model.addAttribute("data", data);
		return "masc/license";
	}

	@RequestMapping(value = "/masc/submit", method = RequestMethod.POST)
	public String submit(@ModelAttribute AuthorizeData data, Model model)
	{
		String uri = data.getRedirect();
		String id = data.getId();
		String name = data.getName();
		String message = String.format("Redirect to %s for client %s", uri, name);
		model.addAttribute("message", message);
		return "info";
	}

	@RequestMapping(value = "/masc/disagree", method = RequestMethod.GET)
	public String disagree(Model model)
	{
		model.addAttribute("message", "The user did not agree to the license terms.");
		return "warning";
	}
}
