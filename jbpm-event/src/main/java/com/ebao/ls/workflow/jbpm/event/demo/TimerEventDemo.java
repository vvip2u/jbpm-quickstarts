package com.ebao.ls.workflow.jbpm.event.demo;

import static com.ebao.ls.workflow.jbpm.event.common.DemoConstants.*;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimerEventDemo {

	public static void main(String[] args) {
		KieHelper kieHelper = new KieHelper();
		KieBase kieBase = kieHelper.addResource(
				ResourceFactory.newClassPathResource(TIMER_EVENT_BPMN_FILE))
				.build();
		
		KieSession ksession = kieBase.newKieSession();
		
		KieRuntimeLogger logger = KieServices.Factory.get()
				.getLoggers().newFileLogger(ksession, TIMER_EVENT_PROCESS_ID);
		
		
		
		ProcessInstance processInstance = ksession.startProcess(TIMER_EVENT_PROCESS_ID);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("process name:       " + processInstance.getProcessName());
		log.info("state:              " + processInstance.getState());
		log.info("process id:         " + processInstance.getParentProcessInstanceId());
		
		logger.close();
		
		ksession.dispose();
		ksession.destroy();
	}

}
