package com.ebao.ls.workflow.jbpm.gateway;

import static com.ebao.ls.workflow.jbpm.gateway.util.DemoConstrants.*;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class ExclusiveDemoTest extends JbpmJUnitBaseTestCase {
    
    @Test
    public void testProcess() {
            
//          createRuntimeManager(BPMN_FILE);
//           
//          RuntimeEngine runtimeEngine = getRuntimeEngine();
//          KieSession ksession = runtimeEngine.getKieSession();
//          ProcessInstance processInstance = ksession.startProcess(PROCESS_ID);
//          
//          assertProcessInstanceCompleted(processInstance.getId());
//          
//          assertProcessInstanceNotActive(processInstance.getId(), ksession);
            
            
            
            RuntimeManager runtimeManager = createRuntimeManager(GATEWAY_BPMN_FILE);
            
            RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
            
            KieSession ksession = runtimeEngine.getKieSession();
            
            AuditService as = runtimeEngine.getAuditService();
            
            ProcessInstance processInstance = ksession.startProcess(GATEWAY_PROCESS_ID);
            
            assertProcessInstanceCompleted(processInstance.getId());
            
            assertProcessInstanceNotActive(processInstance.getId(), ksession);
            
            runtimeManager.disposeRuntimeEngine(runtimeEngine);
            
            runtimeManager.close();
    }

}
