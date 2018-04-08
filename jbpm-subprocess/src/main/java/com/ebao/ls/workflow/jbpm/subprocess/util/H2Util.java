package com.ebao.ls.workflow.jbpm.subprocess.util;

import java.sql.SQLException;

import org.h2.tools.Server;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class H2Util {

	public static PoolingDataSource setupDataSource() {
		String dbUrl = "jdbc:h2:tcp://localhost/~/jbpm-db;MVCC=TRUE";
		PoolingDataSource pds = new PoolingDataSource();
		pds.setUniqueName("jdbc/jbpm-ds");
		pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		pds.setMaxPoolSize(5);
		pds.setAllowLocalTransactions(true);
		pds.getDriverProperties().put("user", "sa");
		pds.getDriverProperties().put("password", "");
		pds.getDriverProperties().put("url", dbUrl);
		pds.getDriverProperties().put("driverClassName", "org.h2.Driver");

		pds.init();
		return pds;
	}

	public static PoolingDataSource setupDataSource(String dsName) {
		String dbUrl = "jdbc:h2:tcp://localhost/~/jbpm-db;MVCC=TRUE";
		PoolingDataSource pds = new PoolingDataSource();
		pds.setUniqueName(dsName);
		pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		pds.setMaxPoolSize(5);
		pds.setAllowLocalTransactions(true);
		pds.getDriverProperties().put("user", "sa");
		pds.getDriverProperties().put("password", "");
		pds.getDriverProperties().put("url", dbUrl);
		pds.getDriverProperties().put("driverClassName", "org.h2.Driver");

		pds.init();
		return pds;
	}

	public static PoolingDataSource setupDataSource(String host, int port) {
		String dbUrl = "jdbc:h2:tcp://" + host + ":" + port + "/~/jbpm-db;MVCC=TRUE";
		PoolingDataSource pds = new PoolingDataSource();
		pds.setUniqueName("jdbc/jbpm-ds");
		pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		pds.setMaxPoolSize(5);
		pds.setAllowLocalTransactions(true);
		pds.getDriverProperties().put("user", "sa");
		pds.getDriverProperties().put("password", "");
		pds.getDriverProperties().put("url", dbUrl);
		pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
		pds.init();
		return pds;
	}

	public static Server startH2Server() {
		try {
			// start h2 in memory database
			Server server = Server.createTcpServer(new String[0]);
			server.start();
			return server;
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}

	public static Server startH2Server2() {
		try {
			// start h2 in memory database
			Server server = Server.createTcpServer();
			server.start();
			return server;
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}

	public static Server startH2ServerSimple() {
		try {
			Server h2Server = Server.createTcpServer(new String[] { "-tcpPort", "9092" });

			h2Server.start();
			if (h2Server.isRunning(true)) {
				System.out.println("H2 server was started and is running.");
			} else {
				throw new RuntimeException("Could not start H2 server.");
			}

			return h2Server;
		} catch (SQLException e) {
			throw new RuntimeException("Failed to start H2 server: ", e);
		}
	}

	public static void main(String[] args) {
		Server server = startH2Server2();
		try {
			server.start();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("========start========");
		System.out.println(server.getStatus());
		System.out.println(server.getPort());
		System.out.println(server.isRunning(true));

		PoolingDataSource ds = setupDataSource();
		try {
			System.out.println(ds);
			System.out.println(ds.getConnection());
			System.out.println(ds.getUniqueName());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		server.shutdown();
		try {
			Thread.sleep(3000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("========shutdown========");
		System.out.println(server.isRunning(true));

		server.stop();
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("========stop========");
		System.out.println(server.isRunning(true));

	}
	
	public static void release(Server server) {
		if(server !=null && server.isRunning(true)) {
			server.shutdown();
		}
	}

}
