package com.ebao.ls.workflow.jbpm.workitemhandler.demo;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieSession;

import com.ebao.ls.workflow.jbpm.workitemhandler.handler.MyTaskHandler;
import com.ebao.ls.workflow.jbpm.workitemhandler.util.JbpmUtil;


/**
 * customer task demo
 * 
 * 
 * JakeCustomTask - custom task name, default definition in /META-INF/JakeCustomTaskDef.wid
 *    name" : "JakeCustomTask"
 * 
 * 
 * 
 * @author jake.jin
 *
 */
public class MyTaskHandlerDemo {

    public static void main(String[] args) {
        KieSession ksession = JbpmUtil.getSessionInsance("handler-demo/handler-demo.bpmn2");
        
        ksession.getWorkItemManager().registerWorkItemHandler("JakeCustomTask", new MyTaskHandler());
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Message", "Hello Message");
        params.put("From", "Jake Home");
        params.put("To", "Charles Home");
        params.put("Priority", "2");
        
        ksession.startProcess("com.ebao.ls.workflow.handler", params);
        
        ksession.dispose();
            
    }


}