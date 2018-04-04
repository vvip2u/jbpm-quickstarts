package com.ebao.ls.workflow.jbpm.complicate;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

import static com.ebao.ls.workflow.jbpm.complicate.DemoConstants.BPMN_FILE;
import static com.ebao.ls.workflow.jbpm.complicate.DemoConstants.PROCESS_ID;

public class ComplicateDemoMain {

	public static void main(String[] args) {
        KieSession ksession = JbpmUtil.getSessionInsance(BPMN_FILE);
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        Order order = new Order();
        order.setAge(18);
        order.setBalance(20d);
        params.put("order", order);
        
//      params.put("age", 18);
//      params.put("balance", 30d);
        
        
        ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, params);
        
        System.out.println("ksession:         " + ksession);
        System.out.println("entry point id:   " + ksession.getEntryPointId());
        System.out.println("processInstance:  " + processInstance);
        
        ksession.fireAllRules();
        
        ksession.dispose();
	}

}
