package com.ebao.ls.workflow.jbpm.subprocess.demo;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.Server;
import org.jbpm.services.task.wih.LocalHTWorkItemHandler;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

import com.ebao.ls.workflow.jbpm.subprocess.util.H2Util;

public class EmbeddedSubProcessDemo {
	
	private static final String JPA_UNITNAME = "org.jbpm.persistence.jpa.subprocess";
	
	public static void main(String[] args) {
		//start H2 Server
		Server h2Server = H2Util.startH2ServerSimple();
		//setup Datasource
		H2Util.setupDataSource("jdbc/jbpm-ds");
		
		
		String[] PROCESS_FILES = { "subprocess-demo/embedded-subprocess-demo.bpmn2" };

		String START_PROCESS_ID = "com.ebao.ls.workflow.jbpm.embedded.subprocess";

		KieBase kbase = null;
		KieSession ksession = null;
		Map<String, Object> parameters = null;
		ProcessInstance process = null;
		EntityManagerFactory emf = null;
		RuntimeEnvironment environment = null;
		RuntimeManager manager = null;
		RuntimeEngine runtime = null;
		LocalHTWorkItemHandler humanTaskHandler = null;
		
		try{
			
			emf = Persistence.createEntityManagerFactory(JPA_UNITNAME);
			environment = RuntimeEnvironmentBuilder.Factory
					.get().newDefaultInMemoryBuilder()
					.entityManagerFactory(emf)
					.addAsset(ResourceFactory.newClassPathResource("subprocess-demo/embedded-subprocess-demo.bpmn2"), 
							ResourceType.BPMN2)
					.get();
			
			manager = RuntimeManagerFactory.Factory
					.get()
					.newSingletonRuntimeManager(environment);
			
			runtime = manager.getRuntimeEngine(EmptyContext.get());
			
			kbase = createKnowledgeBase(PROCESS_FILES);
			
			System.out.println("kieBase:          " + kbase);
			
			ksession = runtime.getKieSession();
			
			humanTaskHandler = new LocalHTWorkItemHandler();    
	        humanTaskHandler.setRuntimeManager(manager);
			
			parameters = null; //getParameters();
			
			// Start the process using its id
			process = ksession.startProcess(START_PROCESS_ID, parameters);
			
			System.out.println("processInstance:   " + process);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			kbase = null;
			process = null;
			
			if(ksession!=null)
				ksession.dispose();
			
			H2Util.release(h2Server);
			
			System.exit(0);
		}
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
