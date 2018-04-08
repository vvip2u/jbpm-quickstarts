package com.ebao.ls.workflow.jbpm.subprocess.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.drools.impl.adapters.ProcessInstanceAdapter;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public class JbpmUtil {

	private static Map<String, KieSession> map = new HashMap<String, KieSession>();

	public static KieSession getSessionInsance(String... processFiles) {
		KieBase kieBase = null;
		KieHelper kieHelper = new KieHelper();

		for (String processFile : processFiles) {
			kieHelper.addResource(ResourceFactory.newClassPathResource(processFile));
		}
		kieBase = kieHelper.build();
		KieSession ksession = kieBase.newKieSession();

		String uuid = UUID.randomUUID().toString();
		System.out.println("uuid:   " + uuid);
		map.put(uuid, ksession);
		return ksession;
	}

	public static Object getVariableFromProcess(ProcessInstance process, String variableName) {
		
//		org.kie.api.runtime.process.ProcessInstance pia = ((ProcessInstanceAdapter) process).getDelegate();
//		WorkflowProcessInstanceImpl rpi = (WorkflowProcessInstanceImpl) pia;
//		return rpi.getVariable(variableName);
		
		RuleFlowProcessInstance rfpi = (RuleFlowProcessInstance) process;
		return rfpi.getVariable(variableName);
		
	}
	
}
