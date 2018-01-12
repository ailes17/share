package com.share.model.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.inject.Inject;

import com.share.common.annotations.Property;
import com.share.common.producers.PropertyProducer;

public class DBCon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8550057842669941756L;

	@Inject
	public PropertyProducer propertyProducer;
	
	// JDBC driver name and database URL
	@Inject
	@Property("model.jdbc.driver.name")
	private String jdbcDriver;
	
	@Inject
	@Property("model.db.url")
	private String dbURL;
	
	// Database credentials
	@Inject
	@Property("model.db.username")
	private String username;
	
	@Inject
	@Property("model.db.password")
	private String password;
	
	public Connection getConnection() {
		Connection conn = null;
		// Register JDBC driver
		try {
			Class.forName(jdbcDriver);
			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected database successfully...");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public void closeConnection(final Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}