package com.ebao.ls.workflow.jbpm.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ebao.ls.workflow.jbpm.spring.service.DemoService;

@RestController
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	
	@RequestMapping(name="/demo", method=RequestMethod.GET)
	public String demo() {
		
		demoService.doJbpm();
		
		return "demo";
	}
}
