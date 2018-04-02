#### I. Introduction

> jBPM is a flexible Business Process Management (BPM) Suite. It makes the bridge between business analysts and developers. Traditional BPM engines have a focus that is limited to non-technical people only. jBPM has a dual focus: it offers process management features in a way that both business users and developers like it.


#### II. Ready
**Tools**
> * [eclipse](http://www.eclipse.org/) ([STS](https://spring.io/tools/sts) as well)
> * database ([H2](http://www.h2database.com/html/main.html)/ [mysql](https://www.mysql.com)/ [Oracle](https://www.oracle.com/index.html) ) 


**Plugins**
- [x] **Drools**   
- Eclipse [Menu](): Help->Eclipse Marketplace, then input the key [Drools]() into the **Find** field  

![drools](https://note.youdao.com/yws/api/personal/file/WEBd12fb9ebbbd94b1abe9a9b5a046e7ecc?method=download&shareKey=42fc841cac0f2b653e980db65cbc3c8c)

Different eclipse version will cause different version of this plugin (the version on My PC is 8.x)


***

#### III. Quick Start
##### HelloWorld
```
[Get-it-Started]()
```
###### dependency
> jbpm-flow     
> jbpm-kie-services     
> jbpm-bpmn2        
> jbpm-audit    
> jbpm-human-task   
> jbpm-human-task-core  
> jbpm-human-task-jpa  
> jbpm-human-task-audit  
> jbpm-human-task-workitems  
> jbpm-test  
> knowledge-api     
> kie-api   
> kie-spring    
> drools-compiler

###### HelloWorld Sample

Hello World **Sample Code:**  
[download](http://www.baidu.com) [to-modify-here]()

###### Codes:
```
        KieBase kieBase = null;
        KieHelper kieHelper = new KieHelper();
        kieHelper.addResource(ResourceFactory.newClassPathResource("hello-world.bpmn"));
        kieBase = kieHelper.build();
		KieSession ksession = kieBase.newKieSession();
		
		//Optional Log, will generate a file named basic-demo.log at the project root dirctory
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "basic-demo");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "jake");
		ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, params);

		log.info("processId:    " + processInstance.getProcessId());
		log.info("processName:  " + processInstance.getProcessName());
		
//		ProcessInstance.STATE_PENDING:   0
//		ProcessInstance.ACTIVE           1
//		ProcessInstance.STATE_COMPLETED  2
//		ProcessInstance.STATE_ABORTED    3
//		ProcessInstance.STATE_ABORTED    4
		
		log.info("state:        " + processInstance.getState());
		ksession.dispose();
		
		logger.close();
```

##### Persistence & Transaction
1. __Persistence__   
    - JPA   
        - [Hibernate](http://hibernate.org/)    
        - [EclipseLink](http://www.eclipse.org/eclipselink/)   
        - [OpenJPA](http://openjpa.apache.org/)   

    We choose Hibernate as the ultimate JPA tool.
2. __Transaction__   
    - [JTA](https://baike.baidu.com/item/jta/9257852?fr=aladdin)
        - [PlatformTransactionManager](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/support/AbstractPlatformTransactionManager.html)    
            - [JpaTransactionManager](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/orm/jpa/JpaTransactionManager.html)
    
    **Configuration: *(META-INF/persistence.xml)***
    ```
        <persistence-unit name="xxxx" transaction-type="JTA">
            ...
        </persistence-unit>
    ```
    
    - NON-JTA     
        - [EntityTransaction](https://docs.oracle.com/javaee/7/api/javax/persistence/EntityTransaction.html)
    
    **Configuration: *(META-INF/persistence.xml)***
    ```
        <persistence-unit name="xxxx" transaction-type="RESOURCE_LOCAL">
            ...
        </persistence-unit>
    ```

3. Datasource
    - JTA
        - [Bitronix](https://github.com/bitronix/btm)  
        ```
		        String dbUrl = "jdbc:h2:~/jbpm";
		        PoolingDataSource pds = new PoolingDataSource();
		        pds.setUniqueName("java:comp/env/jbpm/human-task");
		        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
	        	pds.setMaxPoolSize(5);
		        pds.setAllowLocalTransactions(true);
		        pds.getDriverProperties().put("user", "admin");
		        pds.getDriverProperties().put("password", "admin");
		        pds.getDriverProperties().put("url", dbUrl);
		        pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
		        pds.init();
        ```
        - Atomikos  
    - LOCAL
        - DBCP
        - C3p0

    We choose Bitronix as the ultimate datasource tool.
    
##### Core Functions     
1. __Process__
    1. __Activities__   
        > hello   
        > a     
        > a     

    2. __Event__   
        > hello   
        > a     
        > a
        
    3. __Gateways__
        > hello   
        > a     
        > a     
  

2. __Human Task__



##### Other                                      
***

#### IV. Integration
##### JPA
1. __DB__
    1. __H2__
    2. __Oracle__
    3. __MySQL__
2. __Configuration__
##### Spring
1. __Datasource__
2. __JPA__
3. __Restful WebService__

##### Web Container

```
    System.out.println(“xxx”);
```

***
#### V. Summary





* 进入{jbpm}目录
* 修改eclipse的目录，打开build.properties，修改成自己的安装目录
> 如：eclipse.home=D:\programs\eclipse
* 安装eclipse插件 
> ant install.droolsjbpm-eclipse.into.eclipse
* 安装jbpm runtime插件  
> ant install.jBPM.runtime


