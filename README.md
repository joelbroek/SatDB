# SatDB


A relational database which models satellites, orbital launches, and the relevant organizations and infrastructure

Please note:

Currently, app must be run on UBC CS department servers (i.e. remote.students.cs.ubc.ca), with tunneling to dbhost.students.cs.ubc.ca.
Execute the SatDB.jar, with SatDB_create_database.sql and SatDB_delete_database.sql in the same file directory.

Screenshots of the application are below, as well as in SatDB/screenshots.


The database:

![SatDB entity-relationship diagram](https://github.com/joelbroek/SatDB/blob/master/database-diagram.png)

Some examples of functionality:

![Removing a satellite from the database](https://github.com/joelbroek/SatDB/blob/master/screenshots/delete_sat.png)

![Querying orbits](https://github.com/joelbroek/SatDB/blob/master/screenshots/project_orbit.png)

![A complex query: demonstrating SQL division functionality](https://github.com/joelbroek/SatDB/blob/master/screenshots/division_query.png)
