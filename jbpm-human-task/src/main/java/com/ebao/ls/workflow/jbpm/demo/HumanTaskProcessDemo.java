package com.ebao.ls.workflow.jbpm.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.Server;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilderFactory;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.api.task.model.TaskSummary;

import com.ebao.ls.workflow.jbpm.util.H2Util;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class HumanTaskProcessDemo {
	
	private static Server server;
	public static String BPMN_FILE = "human-task-demo/human-task.bpmn";
	public static String JPA_UNITNAME = "org.jbpm.persistence.jpa.humantask";
	
	public static void main(String[] args) {
		
//		H2Util.startH2Server();
//		H2Util.setupDataSource();
		
		RuntimeManager manager = null;
		RuntimeEngine runtime = null;
        try {
            manager = getRuntimeManager(BPMN_FILE);
            runtime = manager.getRuntimeEngine(null);
            KieSession ksession = runtime.getKieSession();

            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", "krisv");
            params.put("description", "Need a new laptop computer");
            ksession.startProcess("com.sample.humantask", params);

            // "sales-rep" reviews request
            TaskService taskService = runtime.getTaskService();
    		TaskSummary task1 = taskService.getTasksAssignedAsPotentialOwner("sales-rep", "en-UK").get(0);
            System.out.println("Sales-rep executing task " + task1.getName() + "(" + task1.getId() + ": " + task1.getDescription() + ")");
            taskService.claim(task1.getId(), "sales-rep");
            taskService.start(task1.getId(), "sales-rep");
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("comment", "Agreed, existing laptop needs replacing");
            results.put("outcome", "Accept");
            taskService.complete(task1.getId(), "sales-rep", results);

            // "krisv" approves result
            TaskSummary task2 = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK").get(0);
            System.out.println("krisv executing task " + task2.getName() + "(" + task2.getId() + ": " + task2.getDescription() + ")");
            taskService.start(task2.getId(), "krisv");
            results = new HashMap<String, Object>();
            results.put("outcome", "Agree");
            taskService.complete(task2.getId(), "krisv", results);

            // "john" as manager reviews request
            TaskSummary task3 = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK").get(0);
            System.out.println("john executing task " + task3.getName() + "(" + task3.getId() + ": " + task3.getDescription() + ")");
            taskService.claim(task3.getId(), "john");
            taskService.start(task3.getId(), "john");
            results = new HashMap<String, Object>();
            results.put("outcome", "Agree");
            taskService.complete(task3.getId(), "john", results);

            // "sales-rep" gets notification
            TaskSummary task4 = taskService.getTasksAssignedAsPotentialOwner("sales-rep", "en-UK").get(0);
            System.out.println("sales-rep executing task " + task4.getName() + "(" + task4.getId() + ": " + task4.getDescription() + ")");
            taskService.start(task4.getId(), "sales-rep");
            Map<String, Object> content = taskService.getTaskContent(task4.getId());
            for (Map.Entry<?, ?> entry : content.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            taskService.complete(task4.getId(), "sales-rep", null);

    		System.out.println("Process instance completed");
    		
//    		manager.disposeRuntimeEngine(runtime);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        	if(manager!=null) {
        		manager.disposeRuntimeEngine(runtime);
        		manager.close();
        	}
        	
        	if(server!=null) {
        		server.stop();
        		
        		System.exit(0);
        	}
        	
//        	if(runtime!=null) {
//        		runtime.getKieSession().destroy();
//        	}
        }
	}

//	private static Map<String, Object> getParameters() {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		List<String> contentList = new ArrayList<String>();
//		contentList.add("content 1");
//		contentList.add("content 2");
//		contentList.add("content 3");
//
//		parameters.put("contentList", contentList);
//		return parameters;
//	}

    private static RuntimeManager getRuntimeManager(String process) {
//    	System.getProperties().put("java.naming.factory.initial","bitronix.tm.jndi.BitronixInitialContextFactory"); 
    	
        // load up the knowledge base
    	server = H2Util.startH2ServerSimple();
    	System.out.println("================server.start=================");
    	System.out.println("----------------start server-----------------111");
    	
    	PoolingDataSource ds = H2Util.setupDataSource("jdbc/jbpm-ds");
    	System.out.println("=== start H2 Server ===");
    	System.out.println("server:         " + server);
    	System.out.println("datasource:     " + ds);
    	System.out.println("status:         " + server.getStatus());
    	System.out.println("url:            " + server.getURL());
    	try {
			System.out.println("conn:           " + ds.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
//    	System.out.println(ds.getUniqueName());
    	System.out.println("=== started H2 Server ===");
    	
//    	Context envContext = (Context)ctx.lookup("java:/comp/env"); 
        //参数jdbc/mysqlds为数据源和JNDI绑定的名字
//        DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlds"); 
    	
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_UNITNAME);
    	System.out.println("emf:            " + emf);
    	 
    	RuntimeEnvironmentBuilderFactory factory = RuntimeEnvironmentBuilder.Factory.get();
    	System.out.println("RuntimeEnvironmentBuilderFactory:       " + factory);
    	System.out.println(emf.getPersistenceUnitUtil());
    	
    	RuntimeEnvironmentBuilder envBuilder = null;
    	
    	envBuilder = factory.newDefaultBuilder()
            .userGroupCallback(new UserGroupCallback() {
    			public List<String> getGroupsForUser(String userId, List<String> groupIds, List<String> allExistingGroupIds) {
    				List<String> result = new ArrayList<String>();
    				if ("sales-rep".equals(userId)) {
    					result.add("sales");
    				} else if ("john".equals(userId)) {
    					result.add("PM");
    				}
    				return result;
    			}
    			public boolean existsUser(String arg0) {
    				return true;
    			}
    			public boolean existsGroup(String arg0) {
    				return true;
    			}
    		});
    	
    	envBuilder = envBuilder
    			.entityManagerFactory(emf)
                .addAsset(KieServices.Factory.get().getResources()
                		.newClassPathResource(process), ResourceType.BPMN2);
    	
        RuntimeEnvironment environment = envBuilder.get();
        return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);
    }

	public void doEmbedded() throws Exception{
		RuntimeManager manager = null;
		RuntimeEngine runtime = null;
        try {
            manager = getRuntimeManager(BPMN_FILE);        
            runtime = manager.getRuntimeEngine(null);
            KieSession ksession = runtime.getKieSession();

            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", "krisv");
            params.put("description", "Need a new laptop computer");
            ksession.startProcess("com.sample.humantask", params);

            // "sales-rep" reviews request
            TaskService taskService = runtime.getTaskService();
    		TaskSummary task1 = taskService.getTasksAssignedAsPotentialOwner("sales-rep", "en-UK").get(0);
            System.out.println("Sales-rep executing task " + task1.getName() + "(" + task1.getId() + ": " + task1.getDescription() + ")");
            taskService.claim(task1.getId(), "sales-rep");
            taskService.start(task1.getId(), "sales-rep");
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("comment", "Agreed, existing laptop needs replacing");
            results.put("outcome", "Accept");
            taskService.complete(task1.getId(), "sales-rep", results);

            // "krisv" approves result
            TaskSummary task2 = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK").get(0);
            System.out.println("krisv executing task " + task2.getName() + "(" + task2.getId() + ": " + task2.getDescription() + ")");
            taskService.start(task2.getId(), "krisv");
            results = new HashMap<String, Object>();
            results.put("outcome", "Agree");
            taskService.complete(task2.getId(), "krisv", results);

            // "john" as manager reviews request
            TaskSummary task3 = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK").get(0);
            System.out.println("john executing task " + task3.getName() + "(" + task3.getId() + ": " + task3.getDescription() + ")");
            taskService.claim(task3.getId(), "john");
            taskService.start(task3.getId(), "john");
            results = new HashMap<String, Object>();
            results.put("outcome", "Agree");
            taskService.complete(task3.getId(), "john", results);

            // "sales-rep" gets notification
            TaskSummary task4 = taskService.getTasksAssignedAsPotentialOwner("sales-rep", "en-UK").get(0);
            System.out.println("sales-rep executing task " + task4.getName() + "(" + task4.getId() + ": " + task4.getDescription() + ")");
            taskService.start(task4.getId(), "sales-rep");
            Map<String, Object> content = taskService.getTaskContent(task4.getId());
            for (Map.Entry<?, ?> entry : content.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            taskService.complete(task4.getId(), "sales-rep", null);

    		System.out.println("Process instance completed");
    		
//    		manager.disposeRuntimeEngine(runtime);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        	if(manager!=null) {
        		manager.disposeRuntimeEngine(runtime);
        		manager.close();
        	}
        	
        	if(runtime!=null) {
        		runtime.getKieSession().destroy();
        	}
        }
        
        System.out.println("manager:      " + manager);
	}
    
    
//    public static PoolingDataSource setupDataSource() {
//    	String dbUrl = "jdbc:h2:tcp://localhost/~/jbpm-db;MVCC=TRUE";
//        PoolingDataSource pds = new PoolingDataSource();
//        pds.setUniqueName("jdbc/jbpm-ds");
//        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
//        pds.setMaxPoolSize(5);
//        pds.setAllowLocalTransactions(true);
//        pds.getDriverProperties().put("user", "sa");
//        pds.getDriverProperties().put("password", "");
//        pds.getDriverProperties().put("url", dbUrl);
//        pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
//        pds.init();
//        return pds;
//    }
//    
//    public static Server startH2Server() {
//        try {
//            // start h2 in memory database
//            Server server = Server.createTcpServer(new String[0]);
//            server.start();
//            return server;
//        } catch (Throwable t) {
//            throw new RuntimeException("Could not start H2 server", t);
//        }
//    }
//    
//    public static Server startH2ServerSimple() {
//    	 try {
//             Server h2Server = Server.createTcpServer().start();
//             if (h2Server.isRunning(true)) {
//                 System.out.println("H2 server was started and is running.");
//             } else {
//                 throw new RuntimeException("Could not start H2 server.");
//             }
//             
//             return h2Server;
//         } catch (SQLException e) {
//             throw new RuntimeException("Failed to start H2 server: ", e);
//         }
//    }
//    
//    
//    public static void main(String[] args) {
//    	startH2ServerSimple();
//    	PoolingDataSource ds = setupDataSource();
//    	try {
//			System.out.println(ds.getConnection());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}
