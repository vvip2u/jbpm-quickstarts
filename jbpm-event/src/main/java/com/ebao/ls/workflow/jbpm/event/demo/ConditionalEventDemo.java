package com.ebao.ls.workflow.jbpm.event.demo;

import static com.ebao.ls.workflow.jbpm.event.common.DemoConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConditionalEventDemo {

	public static void main(String[] args) {
		KieHelper kieHelper = new KieHelper();
		KieBase kieBase = kieHelper.addResource(
				ResourceFactory.newClassPathResource(CONDITIONAL_EVENT_BPMN_FILE))
				.build();
		
		KieSession ksession = kieBase.newKieSession();
		
		KieRuntimeLogger logger = KieServices.Factory.get()
				.getLoggers().newFileLogger(ksession, CONDITIONAL_EVENT_PROCESS_ID);
		
		ProcessInstance processInstance = ksession.startProcess(CONDITIONAL_EVENT_PROCESS_ID);
		
//		String eventType = "Conditional";
//		String eventType = "transition";
		String eventType = "Signal";
		Map<String, String> data = new HashMap<String, String>();
//		ksession.signalEvent(eventType, data, processInstance.getId());
		
		log.info("process name:       " + processInstance.getProcessName());
		log.info("state:              " + processInstance.getState());
		log.info("process id:         " + processInstance.getParentProcessInstanceId());
		
		logger.close();
	}

}
