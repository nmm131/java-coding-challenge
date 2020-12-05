package com.Java-Coding-Challenge.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
	
	// initialize constant variables
	public static final String databaseFile = "jdbc:sqlite:./src/sqlite/database.db";
	
	public static Connection getConnection() {
		
		// initialize variables
		Connection connection = null;
		
		try {
			// connect to database
			connection = DriverManager.getConnection(databaseFile);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return connection;
	}
}
