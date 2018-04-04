package com.ebao.ls.workflow.jbpm.spring.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

/**
 * a util file for executing some simple test
 * @author jake.jin
 *
 */
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
	
//	public static KieSession start() {
//		ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.escalationeventprocess", parameters);
//	}
	
	
}
