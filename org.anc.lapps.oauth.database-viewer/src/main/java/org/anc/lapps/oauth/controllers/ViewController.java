package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.data.TokenTableData;
import org.anc.lapps.oauth.database.*;
import org.lappsgrid.serialization.Data;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.serialization.lif.Container;
import org.lappsgrid.serialization.lif.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.lappsgrid.discriminator.Discriminators.Uri;

/**
 * @author Keith Suderman
 */
@Controller
public class ViewController
{
	private final Logger logger = LoggerFactory.getLogger(ViewController.class);

	@Autowired
	private ClientDatabase clientDatabase;
	@Autowired
	private AuthorizationCodeDatabase authorizationCodeDatabase;
	@Autowired
	private TokenDatabase tokenDatabase;
	@Autowired
	private ClientTokenDatabase clientTokenDatabase;

//	@PersistenceContext
//	private EntityManager em;

	public ViewController()
	{
//		String sql =
//				  "select t.id, t,token, c.clientId as client " +
//				  "from Token t, Client c, ClientToken ct " +
//				  "where t.id = ct.token AND c.id = ct.client";
//		Query query = em.createNativeQuery(sql, TokenTableData.class);
//		query.executeUpdate();
//		List results = query.getResultList();
	}

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public String clients(Model model)
	{
		model.addAttribute("clients", clientDatabase.findAll());
		return "clients";
	}

	@RequestMapping(value="/clients/delete", method = RequestMethod.GET)
	public String deleteClient(@RequestParam("id")String idString, Model model)
	{
		Long inputId = Long.parseLong(idString);
		Client client = clientDatabase.findById(inputId);
		if (client == null)
		{
			model.addAttribute("clients", clientDatabase.findAll());
			model.addAttribute("error", "Unable to locate client with ID " + idString);
			return "clients";
		}
		clientDatabase.delete(client);
		String message = "Deleted client #" + client.getId();
		logger.debug(message);
		model.addAttribute("message", message);
		model.addAttribute("clients", clientDatabase.findAll());
		return "clients";
	}


	@RequestMapping(value = "/codes", method = RequestMethod.GET)
	public String codes(Model model)
	{
		model.addAttribute("codes", authorizationCodeDatabase.findAll());
		return "codes";
	}

	@RequestMapping(value="/codes/delete", method = RequestMethod.GET)
	public String deleteCode(@RequestParam("id")String idString, Model model)
	{
		Long id;
		AuthorizationCode code;
		try
		{
			id = Long.parseLong(idString);
			code = authorizationCodeDatabase.findById(id);
		}
		catch (Throwable t)
		{
			model.addAttribute("error", "Unable to delete authorization code: " + t.getMessage());
			return codes(model);
		}
		if (code == null)
		{
			model.addAttribute("error", "Unable to find an authorization code with id #" + idString);
			return codes(model);
		}
		authorizationCodeDatabase.delete(code);
		String message = "Deleted authorization code issued to " + code.getIssuedTo();
		logger.debug(message);
		model.addAttribute("message", message);
		model.addAttribute("codes", authorizationCodeDatabase.findAll());
		return "codes";
	}

	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public String tokens(Model model)
	{
		model.addAttribute("tokens", getTokenTableData());
		return "tokens";
	}

	@RequestMapping(value="/tokens/delete", method = RequestMethod.GET)
	public String deleteToken(@RequestParam("id")String idString, Model model)
	{
		Long id = Long.parseLong(idString);
		Token token = tokenDatabase.findById(id);
		if (token == null)
		{
			model.addAttribute("error", "Unable to find a token with id " + id);
			return tokens(model);
		}
		tokenDatabase.delete(token);
//		List<ClientToken> clientToken = clientTokenDatabase.findByToken(token.getId());
		clientTokenDatabase.deleteByToken(token.getId());
		String message = "Deleted token " + token.getId() + " : " + token.getToken();
		logger.debug(message);
		model.addAttribute("message", message);
//		model.addAttribute("tokens", tokenDatabase.findAll());
		model.addAttribute("tokens", getTokenTableData());
		return "tokens";
	}

	protected List<TokenTableData> getTokenTableData()
	{
		List<Token> tokens = tokenDatabase.findAll();
		List<TokenTableData> result = new ArrayList<>(tokens.size());
		for (Token token : tokens)
		{
			ClientToken clientToken = clientTokenDatabase.findByToken(token.getId());
			Client client = clientDatabase.findById(clientToken.getClient());
			TokenTableData data = new TokenTableData(token.getId(), token.getToken(), client.getClientId());
			result.add(data);
		}
		return result;
	}
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test(Model model)
	{
		Container container = new Container();
		container.setText("Hello world");
		View view = container.newView();
		view.addContains(Uri.TOKEN, this.getClass().getName(), "tokenization:gate");
		new AnnotationBuilder(view)
			.start(0)
			.end(5)
			.label("token")
			.type(Uri.TOKEN);
		new AnnotationBuilder(view)
			.start(0)
			.end(5)
			.label("token")
			.type(Uri.TOKEN);

		Data<Container> data = new Data<>(Uri.JSON_LD, container);
		model.addAttribute("json", data.asPrettyJson());
		return "test";
	}

	public static class AnnotationBuilder {
		private Annotation annotation;

		public AnnotationBuilder(View view)
		{
			annotation = view.newAnnotation();
		}

		public AnnotationBuilder start(int start)
		{
			annotation.setStart((long) start);
			return this;
		}
		public AnnotationBuilder end(int end)
		{
			annotation.setEnd((long) end);
			return this;
		}

		public AnnotationBuilder type(String type)
		{
			annotation.setType(type);
			return this;
		}

		public AnnotationBuilder label(String label)
		{
			annotation.setLabel(label);
			return this;
		}

		public Annotation build() { return annotation; }
	}
}
