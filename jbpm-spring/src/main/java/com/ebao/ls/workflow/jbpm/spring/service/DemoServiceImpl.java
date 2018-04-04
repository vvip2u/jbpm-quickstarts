package com.ebao.ls.workflow.jbpm.spring.service;

import static com.ebao.ls.workflow.jbpm.spring.util.DemoConstants.BPMN_FILE;
import static com.ebao.ls.workflow.jbpm.spring.util.DemoConstants.PROCESS_ID;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.stereotype.Service;

import com.ebao.ls.workflow.jbpm.spring.service.DemoService;
import com.ebao.ls.workflow.jbpm.spring.util.JbpmUtil;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	public void doJbpm() {
		KieSession ksession = JbpmUtil.getSessionInsance(BPMN_FILE);
		
		//Optional Log, will generate a file named basic-demo.log at the project root dirctory
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "basic-demo");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "jake");
		ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, params);

		System.out.println("processId:    " + processInstance.getProcessId());
		System.out.println("processName:  " + processInstance.getProcessName());
		
//		ProcessInstance.STATE_PENDING:   0
//		ProcessInstance.ACTIVE           1
//		ProcessInstance.STATE_COMPLETED  2
//		ProcessInstance.STATE_ABORTED    3
//		ProcessInstance.STATE_ABORTED    4
		
		System.out.println("state:        " + processInstance.getState());
		ksession.dispose();
		
		logger.close();
	}

}
