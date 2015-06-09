package org.anc.lapps.oauth.greeter;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.anc.lapps.oauth.database.Client;
import org.anc.lapps.oauth.database.ClientDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	protected ClientDatabase clientDatabase;

	@RequestMapping(value = "/hello", method=RequestMethod.GET)
	public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(value = "/client/list")
	public @ResponseBody List<Client> clientList()
	{
		return clientDatabase.findAll();
	}


}
