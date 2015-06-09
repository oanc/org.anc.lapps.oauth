package org.anc.lapps.oauth.web;

import org.anc.lapps.oauth.Spring;
import org.anc.lapps.oauth.data.ClientData;
import org.anc.lapps.oauth.data.Menu;
import org.anc.lapps.oauth.data.MenuItem;
import org.anc.lapps.oauth.database.Client;
import org.anc.lapps.oauth.database.ClientDatabase;
import org.anc.lapps.oauth.database.TokenDatabase;
import org.anc.lapps.oauth.OAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Controller
@EnableConfigurationProperties
public class PlannerController
{
	@Value("${service.url}")
	private String MY_URL; // = "http://localhost:9000";
//	private static final String MY_URL = "http://grid.anc.org:9080/planner";
	@Value("${service.redirect.url}")
	private String REDIRECT_URL; // = MY_URL + "/authorized";
	@Value("${service.token.url}")
	private String TOKEN_REDIRECT; // = MY_URL + "/tokenized";

	@Value("${resource.url}")
	private String RESOURCE_URL; // = "http://localhost:8080";
	@Value("${resource.token.url}")
	private String TOKEN_URL; // = RESOURCE_URL + "/oauth/token";
	@Value("${resource.authorize.url}")
	private String AUTHORIZE_URL; // = RESOURCE_URL + "/oauth/authorize";

	// For the debug page only
//	@Value("${menu.index}")
//	protected String index;
//	@Value("${menu.client.list}")
//	protected String clientList;
//	@Value("${menu.client.register}")
//	protected String clientRegister;
//	protected MainMenu debugMenu

//	private static final String RESOURCE_URL = "https://webann.ldc.upenn.edu";
//	private static final String TOKEN_URL = RESOURCE_URL + "/grids";
//	private static final String AUTHORIZE_URL = RESOURCE_URL + "/grids/new";

	private final Logger logger = LoggerFactory.getLogger(PlannerController.class);

	private String clientId;
	private String clientSecret;

	@Autowired
	private ClientDatabase clientDatabase;
	@Autowired
	private TokenDatabase tokenDatabase;
	@Autowired
	private MainMenu menu;

	public PlannerController()
	{
		logger.info("Planner controller started.");
//		menu = new MainMenu();
	}

	@RequestMapping(value = "/index",
			  method = RequestMethod.GET,
			  produces = MediaType.TEXT_HTML_VALUE)
	public String index(Model model)
	{
		List<Client> clientList = clientDatabase.findAll();
		List<String> idList = new ArrayList<>();
		if (clientList.size() == 0)
		{
			model.addAttribute("error", "No clients have been registered.");
		}
		else
		{
			for (Client client : clientList)
			{
				idList.add(client.getClientId());
			}
		}
		model.addAttribute("clients", idList);
		model.addAttribute("client", new ClientData());
		model.addAttribute("pageTitle", "Pipeline Planner");
		selectMenu(model, menu.selectPlannerMenu());
		return "planner/index";
	}

	@RequestMapping(value="/debug", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String debug(Model model)
	{
		String[][] debugInfo = {
				  { "Service URL", MY_URL },
				  { "Service Redirect", REDIRECT_URL},
				  { "Service Redirect 2", TOKEN_REDIRECT},
				  { "Resource URL", RESOURCE_URL},
				  { "Resource Authorize", AUTHORIZE_URL},
				  { "Resource Oauth Token", TOKEN_URL},
				  { "Main menu index", menu.index},
				  { "Client menu list", menu.clientList},
				  { "Client menu register", menu.clientRegister}
		};
//		model.addAttribute("service", MY_URL);
//		model.addAttribute("redirect", REDIRECT_URL);
//		model.addAttribute("token", TOKEN_REDIRECT);
//		model.addAttribute("resource", RESOURCE_URL);
//		model.addAttribute("tokenize", TOKEN_URL);
//		model.addAttribute("authorize", AUTHORIZE_URL);

		model.addAttribute("info", debugInfo);
		selectMenu(model, menu.selectPlannerMenu());
		return "planner/debug";
	}

	@RequestMapping(value = "/submit",method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public String submit(@ModelAttribute ClientData clientData, Model model)
	{
		String redirect = REDIRECT_URL;
		Client client = clientDatabase.findByClientId(clientData.getClientId());
		if (client == null)
		{
			logger.error("Client not found in the database: {}", clientData.getClientId());
			index(model);
			model.addAttribute("error", "No such client found.");
			return "planner/index";
		}
		clientId = client.getClientId();
		clientSecret = client.getClientSecret();
		String serverUrl = makeAuthorizeUrl(clientId, redirect);
		logger.info("Redirecting user to {}", serverUrl);
		return "redirect:" + serverUrl;
	}

	@RequestMapping(value = "/authorized")
	public String authorized(@RequestParam(value="code",defaultValue = "") String code, Model model)
	{
		if (code.length() == 0)
		{
			index(model);
			model.addAttribute("error", "Authorization denied.");
			logger.info("Authorization denied.");
			return "planner/index";
		}
		logger.info("Authorized with code {}", code);
		model.addAttribute("code", code);
//		selectMenu(model, plannerMenu);
		selectMenu(model, menu.selectPlannerMenu());
		return "planner/view";
	}

	@RequestMapping(value = "/get-token")
	public String getToken(@RequestParam("code") String code, Model model)
	{
		OAuthToken authToken;
		RestTemplate rest = new RestTemplate();
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//			headers.a

			MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
			body.add("grant_type", "authorization_code");
			body.add("code", code);
			body.add("client_id", clientId);
			body.add("client_secret", clientSecret);
			body.add("redirect_uri", TOKEN_REDIRECT);
//			String url = makeTokenUrl(code);
//			rest.postForObject
//			authToken = rest.getForObject(url, OAuthToken.class);
			HttpEntity form = new HttpEntity<MultiValueMap<String,String>>(body,headers);

			ResponseEntity<OAuthToken> response = rest.exchange(TOKEN_URL, HttpMethod.POST, form, OAuthToken.class);
			authToken = response.getBody();
			//Token token = new Token(authToken.getExpires(), clientId, authToken.getAccess(), authToken.getRefresh());
			// TODO: This should be saved to a database, but currently the
			// planner and resource share the same database!
			model.addAttribute("token", authToken);
			model.addAttribute("client", clientId);
//			selectMenu(model, plannerMenu);
			selectMenu(model, menu.selectPlannerMenu());
			return "planner/granted";
		}
		catch (RestClientException e)
		{
			List<String> errors = new ArrayList();
			errors.add("Error communicating with the remote service.");
			errors.add(e.getMessage());
			model.addAttribute("errors", errors);
			return index(model);
		}

//		return index(model);
	}

	private String makeAuthorizeUrl(String id, String redirect)
	{
		return String.format("%s?client_id=%s&redirect_uri=%s", AUTHORIZE_URL, id, redirect);
	}

	private String makeTokenUrl(String code)
	{
		return String.format("%s?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s", TOKEN_URL, code, clientId, clientSecret);
	}

	private List<MenuItem> add(List<MenuItem> list, String name, String value)
	{
		list.add(new MenuItem(name, value));
		return list;
	}

	private void  selectMenu(Model model, List<Menu> menus)
	{
		model.addAttribute("menus", menus);
	}
}
