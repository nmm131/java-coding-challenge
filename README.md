# Java Coding Challenge

Java Coding Challenge is a Java Application for parsing CSV Files and inserting validated records into an SQLite Database.

Its process follows the steps below:
```
1. Read a CSV file (dataFile.csv)
2. Parse all of its data
3. Validate data with the right number of data elements (10) which matches the columns (A, B, C, D, E, F, G, H, I, J)
4. Insert valid records into an SQLite database (database.db)
5. Write invalid records to a separate CSV file (dataFile-Bad.csv)
6. Write statistics to a log file (database.log)
7. Print execution time
```

## Installation

Download or Clone the repository to your local system using GitHub.com, GitHub Desktop application, Bitbucket.com, GitHub SourceTree application, or .zip

Ensure that the files below exist within "./src/sqlite/" before running the application:
```
database.db
dataFile.csv
dataFile-Bad.csv
```

You can edit db_initialization.sql to view/copy/paste commands into cmd/SQLite3.exe in the case database.db does not exist with a created table called records.

## Usage

Made with Eclipse, Maven and the open source libraries below:
```
1. JUnit
2. SQLite JDBC
3. OpenCSV
```

Java Coding Challenge is re-runnable. Processing is optimized using:
```
1. OpenCSV and SQLite JDBC libraries
2. arrayLists
3. JDBC batch processing
```

More details follow:
```
1. File names are final (constants).
2. CSV header and blank lines are reported, if found, only in Stdout.
3. bufferedReader and preparedStatements are used for optimized performance when handling bulk SQLite INSERT statements.
4. arrayList and a batchCounter variable allow the batch processing to occur every 1000 records or when needed.
5. Batch processing is optimized using transactions and rollback and appropriate close().
6. continue is used in while loop(s) when necessary to skip logic and move onto the next record.
7. Non-string records are simplified for easier insertion into database.
8. Commas are ignored within quotes thanks to OpenCSV library.
9. Base64 images are stored as byte arrays and converted to strings for storage as a Glob within SQLite.
10. A simple JUnit Test exists for JDBC and SQLite insertion.
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
