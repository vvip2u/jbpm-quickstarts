package com.ebao.ls.workflow.jbpm.gateway.demo.loop;

import static com.ebao.ls.workflow.jbpm.gateway.util.DemoConstrants.*;

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
public class LoopDemoMain {

	public static void main(String[] args) {
		KieHelper kieHelper = new KieHelper();
		KieBase kieBase = kieHelper.addResource(ResourceFactory.newClassPathResource(LOOP_BPMN_FILE)).build();
		KieSession ksession = kieBase.newKieSession();

		// Optional Log, will generate a file named basic-demo.log at the project root
		// dirctory
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, LOOP_PROCESS_ID);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("number", 1);

		ProcessInstance processInstance = ksession.startProcess(LOOP_PROCESS_ID, params);

		// logger.(processInstance.getProcessId());
		System.out.println(processInstance.getProcessName());
		System.out.println(processInstance.getState());
		System.out.println(processInstance.getParentProcessInstanceId());
		log.info("======test=====");
		log.error("======test error=====");
		log.debug("======test debug=====");
		ksession.dispose();

		logger.close();

	}

}
