package com.ebao.ls.workflow.jbpm.test.basic;

import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.BPMN_FILE;
import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.END_ID;
import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.PROCESS_ID;
import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.START_ID;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public class BasicDemoTest extends JbpmJUnitBaseTestCase {
	
	@Test
	public void testProcess() {
		 KieHelper kieHelper = new KieHelper();
		 KieBase kieBase = kieHelper
		                    .addResource(ResourceFactory.newClassPathResource(BPMN_FILE))
		                    .build();
		KieSession ksession = kieBase.newKieSession();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "jake");
		ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, params);
		
		assertProcessInstanceCompleted(processInstance.getId());
		assertNodeTriggered(processInstance.getId(), START_ID, "jBPMBasicDemo", END_ID);
	}

}