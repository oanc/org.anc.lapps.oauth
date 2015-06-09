package org.anc.lapps.oauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
@Controller
public class TestController
{
	public TestController()
	{

	}

	@RequestMapping(value = "/test/sections", method = RequestMethod.GET)
	public String sections(Model model)
	{
		List<String> list = new ArrayList<>(4);
		list.add("One");
		list.add("Two");
		list.add("Three");
		list.add("Four");
		model.addAttribute("message", "This page if for testing the wp:section element.");
		model.addAttribute("text", "Some example text that should be inserted into the template.");
		model.addAttribute("list", list);
		return "test/sections";
	}

	@RequestMapping(value = "/test/error", method = RequestMethod.GET)
	public String error(Model model)
	{
		model.addAttribute("message", "This is an error message. Description text should go here.");
		return "error";
	}

	@RequestMapping(value = "/test/warning", method = RequestMethod.GET)
	public String warning(Model model)
	{
		model.addAttribute("message", "This is an warning message. Description text should go here.");
		return "warning";
	}

	@RequestMapping(value = "/test/info", method = RequestMethod.GET)
	public String info(Model model)
	{
		model.addAttribute("message", "This is an informational message. Description text should go here.");
		return "info";
	}

}
