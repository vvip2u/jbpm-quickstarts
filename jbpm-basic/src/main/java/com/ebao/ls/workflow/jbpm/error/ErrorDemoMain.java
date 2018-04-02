package com.ebao.ls.workflow.jbpm.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ebao.ls.workflow.jbpm.error.DemoConstants.*;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;

import com.ebao.ls.workflow.jbpm.util.JbpmUtil;

public class ErrorDemoMain {
	
	public static void main(String[] args) {
		
		List<String> errorList = new ArrayList<String>();
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("inputData", null);
        parameters.put("errorList", errorList) ;
        ProcessInstance process = JbpmUtil.getSessionInsance(BPMN_FILE)
        		.startProcess(PROCESS_ID, parameters);
        
        errorList = (List<String>)((WorkflowProcessInstance)process).getVariable("errorList");
        System.out.println("Error List: " + errorList);
	}
	
}
