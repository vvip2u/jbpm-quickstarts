package com.ebao.ls.workflow.jbpm.basic;

import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.BPMN_FILE;
import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.PROCESS_ID;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

import com.ebao.ls.workflow.jbpm.util.JbpmUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicDemoMain {

	public static final void main(String[] args) throws Exception {
		
		
		KieSession ksession = JbpmUtil.getSessionInsance(BPMN_FILE);
		
		//Optional Log, will generate a file named basic-demo.log at the project root dirctory
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "basic-demo");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "jake");
		ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, params);
		
		log.info("processId:    " + processInstance.getProcessId());
		log.info("processName:  " + processInstance.getProcessName());
		
//		ProcessInstance.STATE_PENDING:   0
//		ProcessInstance.ACTIVE           1
//		ProcessInstance.STATE_COMPLETED  2
//		ProcessInstance.STATE_ABORTED    3
//		ProcessInstance.STATE_ABORTED    4
		
		log.info("state:        " + processInstance.getState());
		ksession.dispose();
		
		logger.close();
	}

}
