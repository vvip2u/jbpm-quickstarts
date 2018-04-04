package com.ebao.ls.workflow.jbpm.humantask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	
	
	@RequestMapping(name="/demo", method=RequestMethod.GET)
	public String demo() {
		return "demo";
	}
}
