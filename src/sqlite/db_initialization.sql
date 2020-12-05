/* create a database */
sqlite3 database.db

/* delete the records table if it exist already - ensuring a clean database*/
DROP TABLE IF EXISTS records;

/* create a table of records */
CREATE TABLE records (
  A TEXT NOT NULL,
  B TEXT NOT NULL,
  C TEXT NOT NULL,
  D INT NOT NULL,
  E BLOB NOT NULL,
  F TEXT NOT NULL,
  G REAL NOT NULL,
  H INT NOT NULL,
  I INT NOT NULL,
  J TEXT NOT NULL
);

/* add sample data 
INSERT INTO records (A, B, C, D, E, F, G, H, I, J) VALUES ('First', 'Last', 'E-Mail@', 1, '0100', 'visa', 9.96, 1, 0, 'Miami');
*/

/* quickly create both database and table */
.exit
sqlite3 database.db
DROP TABLE IF EXISTS records;
CREATE TABLE records (
  A TEXT NOT NULL,
  B TEXT NOT NULL,
  C TEXT NOT NULL,
  D INT NOT NULL,
  E BLOB NOT NULL,
  F TEXT NOT NULL,
  G REAL NOT NULL,
  H INT NOT NULL,
  I INT NOT NULL,
  J TEXT NOT NULL
);