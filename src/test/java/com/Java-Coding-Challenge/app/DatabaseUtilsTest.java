package com.Java-Coding-Challenge.app;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseUtilsTest {
	
	// initialize variables
	private String databaseFile;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	@Before
	public void setup() {
		// connect to database
	    databaseFile = "jdbc:sqlite:./src/sqlite/database.db";
	    try {
            connection = DriverManager.getConnection(databaseFile);
        } catch (SQLException e)  {
        	System.out.println(e.getMessage());
        }
	}
	
	@After
	public void closeDown() {
		// close database
	    try {
	    	if (connection != null) {
	    		connection.close();
	    	}
        } catch (SQLException e)  {
        	System.out.println(e.getMessage());
        }
	}
	
	@Test
    public void databaseInitializationTest() {
        
		// connect to database
        try {
            connection = DatabaseUtils.getConnection();
            statement = connection.createStatement();
            // insert a record into SQLite database
            statement.executeUpdate("INSERT INTO records (A, B, C, D, E, F, G, H, I, J) VALUES ('Not_A_Real_Name_0100', 'Last', 'E-Mail@', 1, '0100', 'visa', 9.96, 1, 0, 'Miami');");
            resultSet = statement.executeQuery("select * from records where A = 'Not_A_Real_Name_0100'");
            
            // check inserted record
            while(resultSet.next()) {
                String A = resultSet.getString("A");
                // assert that the record data is correct
                assertEquals("The column A is correct.", "Not_A_Real_Name_0100", A);
            }
            // remove the record from the database
            statement.executeUpdate("delete from records where A = 'Not_A_Real_Name_0100'");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}