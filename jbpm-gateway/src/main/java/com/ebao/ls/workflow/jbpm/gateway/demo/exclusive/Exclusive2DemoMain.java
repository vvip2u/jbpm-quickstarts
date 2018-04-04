package com.ebao.ls.workflow.jbpm.gateway.demo.exclusive;

import static com.ebao.ls.workflow.jbpm.gateway.util.DemoConstrants.*;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exclusive2DemoMain {
	
    public static void main(String[] args) {
        
        KieHelper kieHelper = new KieHelper();
        KieBase kieBase = kieHelper.addResource(
                        ResourceFactory.newClassPathResource(EXCLUSIVE_BPMN2_FILE))
                        .build();
        KieSession ksession = kieBase.newKieSession();
        
        //Optional Log, will generate a file named basic-demo.log at the project root dirctory
        KieRuntimeLogger logger = KieServices.Factory.get()
                                                                .getLoggers().newFileLogger(ksession, EXCLUSIVE_PROCESS_ID);
        
        ProcessInstance processInstance = ksession.startProcess(EXCLUSIVE_PROCESS_ID);

        log.info("process name:  " + processInstance.getProcessName());
        log.info("state:         " + processInstance.getState());
        log.info("process id:    " + processInstance.getParentProcessInstanceId());
        log.info("parent id:     " + processInstance.getParentProcessInstanceId());
        
        ksession.dispose();
        
        logger.close();
        
}

	
}
