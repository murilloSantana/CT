package br.com.cta.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/home"})
public class CtrlHome {

	@RequestMapping(value = { "","/" }, method = RequestMethod.GET)
	public String hello() throws IOException {
		return "index";
	}
}
