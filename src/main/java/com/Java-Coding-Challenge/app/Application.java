package com.Java-Coding-Challenge.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

public class Application {
	
	// initialize constant variables
	public static final String dataFile = "./src/sqlite/dataFile.csv";
	public static final String dataFileBad = "./src/sqlite/dataFile-bad.csv";
	public static final String databaseLog = "database.log";
	
	// main
	public static void main (String args[]) throws SerialException, SQLException, IOException {
		
		// initialize variables
		long startTime = System.nanoTime();
		
		String[] header = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		String[] nextRecord;
		List<String[]> recordData = new ArrayList<>();
		byte[] blobBytes = null;
		byte[] decodedBlob = null;
		
		int recordsReceived = 0;
		int recordsSuccessful = 0;
		int recordsFailed = 0;
		boolean foundHeader = false;
		boolean foundBlankLines = false;
		boolean foundMissingField = false;
		
		try (
				// create csv reader
				Reader reader = Files.newBufferedReader(Paths.get(dataFile));
				CSVReader csvReader = new CSVReader(reader);
				
				// create csv writer which writes to next available line
				Writer writer = Files.newBufferedWriter(Paths.get(dataFileBad), StandardOpenOption.APPEND);
				CSVWriter csvWriter = new CSVWriter(writer,
	                    CSVWriter.DEFAULT_SEPARATOR,
	                    CSVWriter.DEFAULT_QUOTE_CHARACTER, // ignore commas within quotes
	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                    CSVWriter.DEFAULT_LINE_END);
			) {
			// read csv file row by row
			System.out.println("Reading and writing data. Please wait...");
			while ((nextRecord = csvReader.readNext()) != null) {
				foundMissingField = false;
				// recordsReceived is inclusive of header, blank lines, received/successful/failed records
				// could be moved lower in the while loop to only reflect received/successful/failed records
				recordsReceived++;
				
				// check for header
				if (Arrays.equals(nextRecord, header)) {
					if (!foundHeader) {
						System.out.println("WARNING: Data File contains header.");
					}
					foundHeader = true;
					continue;
				} // check for blank lines
				else if ((nextRecord.length == 1) && (nextRecord[0].isEmpty()) && (!foundBlankLines)) {
					System.out.println("WARNING: Data File contains blank lines.");
					foundBlankLines = true;
					continue;
				} 
				
				// check for missing fields
				for (int i = 0; i < 10; i++) {
					if (nextRecord[i].isEmpty()) {
						foundMissingField = true;
						break;
					}
				}

				if (foundMissingField) {
					// add failed records to dataFile-Bad.csv
					csvWriter.writeNext(new String[] {nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], 
							nextRecord[4], nextRecord[5], nextRecord[6], nextRecord[7], nextRecord[8], nextRecord[9]});
					recordsFailed++;
				} else {
					// simplify non-string record data for insertion into database
					if (nextRecord[3].toUpperCase().contentEquals("MALE")) {
						nextRecord[3] = "1";
					} else if (nextRecord[3].toUpperCase().contentEquals("FEMALE")) {
						nextRecord[3] = "0";
					}
					blobBytes = Base64.getEncoder().encode(nextRecord[4].getBytes());
					decodedBlob = Base64.getDecoder().decode(new String(blobBytes).getBytes("UTF-8"));
					nextRecord[4] = decodedBlob.toString();
					nextRecord[6] = nextRecord[6].replace("$", "");
					if (nextRecord[7].toUpperCase().contentEquals("TRUE")) {
						nextRecord[7] = "1";
					} else if (nextRecord[7].toUpperCase().contentEquals("FALSE")) {
						nextRecord[7] = "0";
					}
					if (nextRecord[8].toUpperCase().contentEquals("TRUE")) {
						nextRecord[8] = "1";
					} else if (nextRecord[8].toUpperCase().contentEquals("FALSE")){
						nextRecord[8] = "0";
					}
					
					// add successful records to array list containing string arrays
					recordData.add(nextRecord);
					recordsSuccessful++;
				}
			}
			
			// add array list records into SQLite database
			DatabaseService.addRecordsToDatabase(recordData);
			}
		
			// write statistics to .log file
			FileWriter fileWriter = new FileWriter(databaseLog);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("# of records received: " + recordsReceived + " (NOTE: inclusive of header/blank/successful/failed records)\n# of records successful: " + 
			recordsSuccessful + "\n# of records failed: " + recordsFailed);
			bufferedWriter.close();
			
			// print execution time in nanoseconds
			long endTime = System.nanoTime();
			System.out.println("Took "+(endTime - startTime) + " ns"); 
	}

}
