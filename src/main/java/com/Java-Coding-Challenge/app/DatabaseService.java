package com.Java-Coding-Challenge.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseService {
	
	public static void addRecordsToDatabase(List<String[]> recordData) {
		
		// initialize variables
		String queryString = "INSERT INTO records(A, B, C, D, E, F, G, H, I, J) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int batchCount = 0;
		
		try {
			// connect to database
			connection = DatabaseUtils.getConnection();
			
			// begin transaction
			connection.setAutoCommit(false);
			
			// create statement
			preparedStatement = connection.prepareStatement(queryString);
			
			// set statement
			for (String[] row : recordData) {
				preparedStatement.setString(1, row[0]);
				preparedStatement.setString(2, row[1]);
				preparedStatement.setString(3, row[2]);
				preparedStatement.setInt(4, Integer.parseInt(row[3]));
				// set blob
				preparedStatement.setBytes(5, row[4].getBytes());
				preparedStatement.setString(6, row[5]);
				preparedStatement.setDouble(7, Double.parseDouble(row[6]));
				preparedStatement.setInt(8, Integer.parseInt(row[7]));
				preparedStatement.setInt(9, Integer.parseInt(row[8]));
				preparedStatement.setString(10, row[9]);
			
				// add batch
				preparedStatement.addBatch();
				
				// execute batch
				if ((batchCount++ % 1000 == 0) || (batchCount == recordData.size())) {
					preparedStatement.executeBatch();
					
					// end transaction
					connection.commit();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// rollback changes
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		} finally {
			try {
				// close statement
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				// close connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
