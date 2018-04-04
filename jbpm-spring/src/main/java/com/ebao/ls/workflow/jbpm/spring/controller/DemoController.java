package com.ebao.ls.workflow.jbpm.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ebao.ls.workflow.jbpm.spring.service.DemoService;

@RestController
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	
	@RequestMapping(name="/demo", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> demo() {
		
		demoService.doJbpm();
		
		Map<String,Object> map = new HashMap<String,Object>();  
	    map.put("message", "JBPM finished");  
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
}
