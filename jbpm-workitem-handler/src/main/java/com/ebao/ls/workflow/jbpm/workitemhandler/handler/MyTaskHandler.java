package com.ebao.ls.workflow.jbpm.workitemhandler.handler;

import java.util.Map;
import java.util.Set;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class MyTaskHandler implements WorkItemHandler {

        @Override
        public void abortWorkItem(WorkItem wi, WorkItemManager manager) {
                manager.abortWorkItem(wi.getId());
        }

        @Override
        public void executeWorkItem(WorkItem wi, WorkItemManager manager) {
                
                Map<String, Object> params = wi.getParameters();
                Set<String> keySet = params.keySet();
                
                String textFormat = "%s / %s";
                
                System.out.println("key/value list:");
                for (String key : keySet) {
                        System.out.println(String.format(textFormat, key, params.get(key)));
                }
                System.out.println("DemoHandler.executeWorkItem()");
                manager.completeWorkItem(wi.getId(), null);
        }

}
