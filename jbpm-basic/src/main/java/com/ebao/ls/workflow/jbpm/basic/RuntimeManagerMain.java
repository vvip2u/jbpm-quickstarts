package com.ebao.ls.workflow.jbpm.basic;

import static com.ebao.ls.workflow.jbpm.basic.DemoConstants.PROCESS_ID;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.internal.utils.KieHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeManagerMain {
	
	
//	@Inject
	@Singleton
	private RuntimeManager singletonManager;
	
	public static void main(String[] args) {

		RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory
				.get().newDefaultInMemoryBuilder()
				.addAsset(ResourceFactory.newClassPathResource("basic-demo.bpmn"),
						ResourceType.BPMN2).get();
		
		RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);
		
		RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());
		
		KieSession ksession = runtime.getKieSession();
		
		manager.disposeRuntimeEngine(runtime);
		

		// Optional Log, will generate a file named basic-demo.log at the
		// project root dirctory
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers()
				.newFileLogger(ksession, "basic-demo");

		ProcessInstance processInstance = ksession.startProcess(PROCESS_ID);

		log.info("process name:  " + processInstance.getProcessName());
		log.info("state:         " + processInstance.getState());
		log.info("process id:    " + processInstance.getParentProcessInstanceId());
		log.info("parent id:     " + processInstance.getParentProcessInstanceId());
		
		ksession.dispose();
		
		logger.close();
		
		
	}

}
