package org.anc.lapps.oauth.grant;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Keith Suderman
 */
@Controller
public class GrantController
{
	public GrantController()
	{

	}

	@RequestMapping(value = "/request", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String request()
	{

		return "greet";
	}
}
