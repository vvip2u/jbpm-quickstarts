package com.ebao.ls.workflow.jbpm.subprocess.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import com.ebao.ls.workflow.jbpm.subprocess.util.JbpmUtil;

public class SimpleSubProcessDemo {

	public static void main(String[] args) {
		String[] PROCESS_FILES = { "subprocess-demo/simple-subprocess.bpmn2",
				"subprocess-demo/simple-subprocess-child.bpmn2" };

		String START_PROCESS_ID = "com.ebao.ls.workflow.jbpm.simple.subprocess";

		KieBase kbase = null;
		KieSession ksession = null;
		Map<String, Object> parameters = null;
		ProcessInstance process = null;
		
		try{
			kbase = createKnowledgeBase(PROCESS_FILES);
			ksession = createKnowledgeSession(kbase);
			
			parameters = getParameters();
			
			// Start the process using its id
			process = ksession.startProcess(START_PROCESS_ID, parameters);
			
			Object messagesVar = JbpmUtil.getVariableFromProcess(process, "contentList");
			System.out.println(messagesVar);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ksession!=null)
				ksession.dispose();
		}
	}

	private static Map<String, Object> getParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<String> contentList = new ArrayList<String>();
		contentList.add("content 1");
		contentList.add("content 2");
		contentList.add("content 3");

		parameters.put("contentList", contentList);
		return parameters;
	}

	private static KieSession createKnowledgeSession(KieBase kbase) {
		return kbase.newKieSession();
	}

	private static KieBase createKnowledgeBase(String... process) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		for (String p : process) {
			kbuilder.add(ResourceFactory.newClassPathResource(p), ResourceType.BPMN2);
		}

		if (kbuilder.hasErrors()) {
			if (kbuilder.getErrors().size() > 0) {
				throw new RuntimeException("Create KnowledgeBuilder Error," + kbuilder.getErrors().toString());
			}
		}

		return kbuilder.newKnowledgeBase();
	}

}
