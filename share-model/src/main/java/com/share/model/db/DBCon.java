package com.share.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

	// Database credentials
	static final String USER = "adel";
	static final String PASS = "adel123";

	public Connection getConnection() {
		Connection conn = null;
		// STEP 2: Register JDBC driver
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/share", USER, PASS);
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