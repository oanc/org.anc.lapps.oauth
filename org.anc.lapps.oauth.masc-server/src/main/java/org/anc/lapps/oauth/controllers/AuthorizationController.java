package org.anc.lapps.oauth.controllers;

import org.anc.lapps.oauth.database.*;
import org.anc.lapps.oauth.OAuthToken;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Controller
public class AuthorizationController
{
	private final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private ClientDatabase clientDatabase;
//	@Autowired
//	private UserDatabase userDatabase;
	@Autowired
	private AuthorizationCodeDatabase authorizationCodeDatabase;
	@Autowired
	private TokenDatabase tokenDatabase;
	@Autowired
	private ClientTokenDatabase clientTokenDatabase;

	public AuthorizationController()
	{
		System.out.println("Authorization controller created.");
	}

	@RequestMapping(value="/greet", method = RequestMethod.GET)
	public String greet(@QueryParam("name") String name, Model model)
	{
		if (name == null)
		{
			model.addAttribute("message", "Goodbye cruel world.");
		}
		else
		{
			model.addAttribute("message", "Hello " + name);
		}
		return "greet";
	}

	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello()
	{
		return "hello";
	}

	@RequestMapping(value="/blank", method = RequestMethod.GET)
	public String blank()
	{
		return "blank";
	}

//	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
//	public @ResponseBody String post_addUser(@RequestParam("name")String name, @RequestParam("password")String password)
//	{
//		User user = new User(name, password);
//		userDatabase.save(user);
////		return user;
//		return "OK";
//	}

//	@RequestMapping(value="/user/list", method = RequestMethod.GET)
//	public @ResponseBody List<User> list()
//	{
//		List<User> users = new ArrayList<>();
//		for (User user : userDatabase.findAll())
//		{
//			users.add(user);
//		}
//		return users;
//	}

	@RequestMapping(value = "/oauth/authorize")
	public String oauthAuthorize(
			  @RequestParam("client_id") String clientId,
			  @RequestParam("redirect_uri") String redirect,
			  Model model)
	{
		model.addAttribute("clientId", clientId);
		model.addAttribute("redirect", redirect);
		return "authorize";
	}

//	@RequestMapping(value = "/oauth/submit", method = RequestMethod.POST)
//	public String oauthSubmit()
//	{
//		return null;
//	}

	@RequestMapping(value = "/oauth/submit", method = RequestMethod.POST)
	public String authorize() throws OAuthSystemException, URISyntaxException
	{
		logger.debug("Incoming authorization request.");
		Response response;
		HttpServletRequest request = getRequest();
		if (request == null)
		{
			return "oauth";
//			return Response.serverError().entity("Null Pointer Exception.").build();
		}
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getHeader(name);
			logger.debug("Header: {} = {}", name, value);
		}
		logger.debug("Method: {}", request.getMethod());
		logger.debug("Query : {}", request.getQueryString());
		logger.debug("Path  : {}", request.getRequestURL());
		try {
			OAuthAuthzRequest oauthRequest =
					  new OAuthAuthzRequest(request);
			OAuthIssuerImpl oauthIssuerImpl =
					  new OAuthIssuerImpl(new MD5Generator());

			//build response according to response_type
			String responseType =
					  oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

			OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
					  OAuthASResponse.authorizationResponse(request,
								 HttpServletResponse.SC_FOUND);

			String clientId = oauthRequest.getClientId();
			// 1
//			if (responseType.equals(ResponseType.CODE.toString())) {
				final String authorizationCode =
						  oauthIssuerImpl.authorizationCode();
				//TODO Check if this client already has an authorization code and return that instead.
				logger.info("Authorization code {}", authorizationCode);
				AuthorizationCode code = new AuthorizationCode(authorizationCode, clientId);
				authorizationCodeDatabase.save(code);
//				database.addAuthCode(authorizationCode);
				builder.setCode(authorizationCode);
//			}

			String redirectURI =
					  oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			logger.debug("Redirect is {}", redirectURI);
			final OAuthResponse authResponse = builder
					  .location(redirectURI)
					  .buildQueryMessage();
			URI url = new URI(authResponse.getLocationUri());
			logger.debug("Redirecting to {}", url);
			return "redirect:" + url.toString();
//			int status = authResponse.getResponseStatus();
//			logger.debug("Status is {}", status);
//			response = Response.status(status)
//					  .location(url)
//					  .build();
		} catch (OAuthProblemException e ) {
			logger.error("Authorization problem.", e);
			response = Response.serverError().entity(e.getMessage()).build();
		}
//		logger.info(response.toString());
		return "oauth";
	}

	@RequestMapping(value="/oauth/token", produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public OAuthToken oauthToken(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException
	{
//		HttpServletRequest request = getRequest();
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getHeader(name);
			logger.debug("Header: {} = {}", name, value);
		}
		names = request.getParameterNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getParameter(name);
			logger.debug("Parameter: {} = {}", name, value);
		}
		try {
			BufferedReader reader = request.getReader();
			String line = reader.readLine();
			while (line != null)
			{
				logger.debug("Body: {}", line);
				line = reader.readLine();
			}
			OAuthTokenRequest oauthRequest =
					  new OAuthTokenRequest(request);
			OAuthIssuer oauthIssuerImpl =
					  new OAuthIssuerImpl(new MD5Generator());

			String clientId = oauthRequest.getClientId();
			String clientSecret = oauthRequest.getClientSecret();
			Client client = checkClient(clientId, clientSecret);
			if (client == null)
			{
				response.setStatus(400);
				response.addHeader("error", "invalid_client");
				return new OAuthToken();
//				return Response.status(400).header("error", "invalid_client").build();
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!checkAuthCode(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
//					return buildBadAuthCodeResponse();
					response.sendError(400, "Invalid authorization code.");
					return new OAuthToken();
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.PASSWORD.toString())) {
//				if (!checkUserPass(oauthRequest.getUsername(),
//						  oauthRequest.getPassword())) {
//					return buildInvalidUserPassResponse();
//				}
				response.sendError(400, "Password grants are not accepted. Please obtain an authorization code.");
				return new OAuthToken();
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.REFRESH_TOKEN.toString())) {
				// refresh token is not supported in this implementation
				//return buildInvalidUserPassResponse();
//				return Response.status(400).entity("Refresh tokens are not currently supported.").build();
				response.sendError(400, "Refresh tokens are not currently supported.");
				return new OAuthToken();
			}

			// The user has the correct credentials. Generate a new access
			// token and the HTTP response.
			final String accessToken = oauthIssuerImpl.accessToken();
			final String refreshToken = oauthIssuerImpl.refreshToken();
			Token token = new Token(3600L, accessToken, refreshToken);
			tokenDatabase.save(token);
			clientTokenDatabase.save(new ClientToken(token.getId(), client.getId()));
			response.setStatus(200);
			return new OAuthToken(3600L, accessToken, refreshToken);
//			OAuthResponse response = OAuthASResponse
//					  .tokenResponse(HttpServletResponse.SC_OK)
//					  .setAccessToken(accessToken)
//					  .setExpiresIn("3600")
//					  .buildJSONMessage();
//			return Response.status(response.getResponseStatus())
//					  .entity(response.getBody()).build();

		} catch (OAuthProblemException e) {
			logger.error("OAuth: problem getting access/refresh tokens.", e);
			try
			{
				response.sendError(400, e.getMessage());
			}
			catch (IOException e1)
			{
				logger.error("Error sending the error response!", e1);
			}
			return new OAuthToken();
//			OAuthResponse res = OAuthASResponse
//					  .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
//					  .error(e)
//					  .buildJSONMessage();
//			return Response
//					  .status(res.getResponseStatus()).entity(res.getBody())
//					  .build();
		}
		catch (IOException e)
		{
			logger.error("Error generating an error response.", e);
			return new OAuthToken();
//			e.printStackTrace();
		}
		//return Response.serverError().entity("Programming error. Execution should not reach this point.").build();
	}

	@RequestMapping(value="/old/token",
			  produces = MediaType.APPLICATION_JSON,
			  consumes = MediaType.APPLICATION_FORM_URLENCODED)
	public Response token() throws OAuthSystemException
	{
		HttpServletRequest request = getRequest();
		try {
			OAuthTokenRequest oauthRequest =
					  new OAuthTokenRequest(request);
			OAuthIssuer oauthIssuerImpl =
					  new OAuthIssuerImpl(new MD5Generator());

			String clientId = oauthRequest.getClientId();
			String clientSecret = oauthRequest.getClientSecret();
			Client client = checkClient(clientId, clientSecret);
			if (client != null)
			{
				return Response.status(400).header("error", "invalid_client").build();
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!checkAuthCode(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
					return buildBadAuthCodeResponse();
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.PASSWORD.toString())) {
				if (!checkUserPass(oauthRequest.getUsername(),
						  oauthRequest.getPassword())) {
					return buildInvalidUserPassResponse();
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					  .equals(GrantType.REFRESH_TOKEN.toString())) {
				// refresh token is not supported in this implementation
				//return buildInvalidUserPassResponse();
				return Response.status(400).entity("Refresh tokens are not currently supported.").build();
			}

			// The user has the correct credentials. Generate a new access
			// token and the HTTP response.
			final String accessToken = oauthIssuerImpl.accessToken();
			//TODO if we start using this again this line needs to be fixed.
//			tokenDatabase.save(new Token(accessToken, clientId));

			OAuthResponse response = OAuthASResponse
					  .tokenResponse(HttpServletResponse.SC_OK)
					  .setAccessToken(accessToken)
					  .setExpiresIn("3600")
					  .buildJSONMessage();
			return Response.status(response.getResponseStatus())
					  .entity(response.getBody()).build();

		} catch (OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse
					  .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					  .error(e)
					  .buildJSONMessage();
			return Response
					  .status(res.getResponseStatus()).entity(res.getBody())
					  .build();
		}
		//return Response.serverError().entity("Programming error. Execution should not reach this point.").build();
	}

	private Response buildInvalidUserPassResponse()
	{
		return Response.status(400).entity("invalid user or password").build();
	}

	private boolean checkUserPass(String username, String password)
	{
		return true;
//		User user = userDatabase.findByName(username);
//		try
//		{
//			return PasswordHash.validate(password, user.getPassword());
//		}
//		catch (org.anc.lapps.oauth.security.SecurityException e)
//		{
//			//TODO Log this error.
//			e.printStackTrace();
//			return false;
//		}
	}

	private Response buildBadAuthCodeResponse()
	{
		return Response.status(500).entity("Bad authorization code.").build();
	}

	private boolean checkAuthCode(String param)
	{
		return true;
//		AuthorizationCode code = authorizationCodeDatabase.findByCode(param);
//		return code != null;
	}

	private Client checkClient(String clientId, String clientSecret)
	{
//		return true;
		if (clientId == null || clientSecret == null)
		{
			return null;
		}

		Client client = clientDatabase.findByClientId(clientId);
		if (client != null && client.getClientSecret().equals(clientSecret))
		{
			return client;
		}
		return null;
	}

	private HttpServletRequest getRequest()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}
}
