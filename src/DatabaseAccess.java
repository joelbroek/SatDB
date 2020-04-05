import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Vector;



public class DatabaseAccess {
    private SatDB satDB;
    private Connection conn = null;
    private int launchRequestNumber;

    public DatabaseAccess(SatDB caller) {
        launchRequestNumber = 20;
        satDB = caller;

        // TODO - THIS IS TEMPORARY UNTIL LOGIN IS IMPLEMENTED
        try {
            satDB.displayMesssage("Connecting to database...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu", "ora_jbroek", "a50121862");
            conn.setAutoCommit(false);
            satDB.displayMesssage("Connection successful.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            satDB.displayError(e.getMessage());
        }
    }

    public void initialise() throws Exception {
        try {
            executeScript("SatDB_delete_database.sql");
            executeScript("SatDB_create_database.sql");
            satDB.displayMesssage("Database configuration completed.");
        } catch (Exception e) {
            satDB.displayError("Error occurred at initialise: " + e.getMessage());
            throw e;
        }
//        disconnect();
    }


    public boolean login(String username, String password) {
        try {
            if (conn != null) {
                conn.close();
            }
            satDB.displayMesssage("Connecting to database...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu", username, password);
            conn.setAutoCommit(false);
            satDB.displayMesssage("Connection successful.");
            return true;
        } catch (Exception e) {
            satDB.displayError("Login error: " + e.getMessage());
            return false;
        }
    }

    public void disconnectAndResetDatabase() {
        try {
            reset();
            disconnect();
        } catch (Exception e) {
            satDB.displayError(e.getMessage());
        }
    }

    private void reset() {
        try {
            executeScript("SatDB_delete_database.sql");
        } catch (Exception e) {
            satDB.displayError(e.getMessage());
        }
    }


    private void disconnect(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            satDB.displayError("Exception occurred at disconnect: " + e.getMessage());
        }
    }

    // executes a .sql script file. Assumes statements are semicolon-separated
    private void executeScript(String filePath) throws Exception {
        StringBuffer buff = new StringBuffer();

        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(reader);
            String str = "";
            while ((str = buffReader.readLine()) != null) {
                buff.append(str);
            }

            String[] instructions = buff.toString().split(";");
            Statement stmt = conn.createStatement();
            for (String instr : instructions) {
                stmt.executeUpdate(instr);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Performs SQL 'UPDATE' statements
    private boolean performUpdate(String sqlString) {
        Statement stmt = null;
        satDB.displayMesssage("UPDATE: \n" + sqlString);
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlString);
            return true;
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
            rollback();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                satDB.displayError(e.getMessage());
            }
        }
    }

    // Performs SQL queries
    private JTable performQuery(String sqlString) {
        Statement stmt = null;
        satDB.displayMesssage("QUERY: \n" + sqlString);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            return new JTable(formatResultSet(rs));
        } catch(SQLException e) {
            satDB.displayError(e.getMessage());
            rollback();
            return new JTable();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                satDB.displayError(e.getMessage());
            }
        }
    }

    private void rollback() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
        }
    }

    // Constructs DefaultTableModel from ResultSet
    private DefaultTableModel formatResultSet(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numCols = metaData.getColumnCount();
            Vector<String> colNames = new Vector<>();
            Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
            for (int i = 1; i <= numCols; i++) {
                colNames.add(metaData.getColumnName(i));
            }
            while (rs.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i <= numCols; i++) {
                    v.add(rs.getObject(i));
                }
                tableData.add(v);
            }
            return new DefaultTableModel(tableData, colNames);
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
            return null;
        }

    }


    public JTable insertLaunchRequest(String launchSystem, int satID, String agencyID, String date) {
        int launchID = launchRequestNumber;
        launchRequestNumber++;
        if (performUpdate("INSERT INTO launch_request(id, is_approved, launch_system, sat_id, agency_id, scheduled_date) VALUES ("
                + launchID
                + ", 0, '"
                + launchSystem
                + "', " + satID + ", '"
                + agencyID + "', '"
                + date + "')"
        )) {
            return performQuery("SELECT id, is_approved, launch_system, sat_id, agency_id, scheduled_date FROM launch_request");
        } else {
            satDB.displayError("failed to insert launch request");
            return new JTable(1,1);
        }
    }

    // Deletes satellite with given ID, then retrieves all Satellites
    public JTable deleteSatellite(int satelliteID) {
        if (performUpdate("DELETE FROM satellite WHERE id = " + satelliteID)) {
            return performQuery("SELECT id FROM satellite");
        } else {
            satDB.displayError("Failed to delete satellite");
            return new JTable(1, 1);
        }
    }

    public JTable updateConstellation(String purpose, String name) {
        if (performUpdate("UPDATE constellation SET purpose = '" + purpose + "' WHERE name = '" + name + "'")) {
            return performQuery("SELECT name, purpose FROM constellation");
        } else {
            satDB.displayError("Failed to update constellation");
            return new JTable();
        }
    }

    public JTable selectSatellite(String orbitType) {
        return performQuery("SELECT id, name, constellation FROM satellite WHERE orbit_type = '" + orbitType + "'");
    }

    public JTable projectFromOrbit(String field) {
        return performQuery("SELECT id, " + field + " FROM orbit");
    }

    public JTable joinQuery() {
        return performQuery("SELECT satellite.id, constellation.purpose FROM satellite " +
                "INNER JOIN constellation ON satellite.constellation = constellation.name");
    }

    public JTable aggregationQuery() {
        return performQuery("SELECT MAX(eccentricity) AS HighestEccentricity FROM Orbit");
    }

    public JTable nestedAggregationQuery() {
        return performQuery("SELECT Avg(sum (is_approved)) AS AverageTotalApprovals FROM launch_request Group by sat_id");
    }

    public JTable divisionQuery() {
        return performQuery("SELECT a.name " +
                "FROM space_agency a " +
                "WHERE " +
                "NOT EXISTS ( (SELECT a1.id FROM space_agency a1) MINUS (SELECT a2.id FROM space_agency a2, launch_request lr2 WHERE a1.id = lr2.agency AND lr1.is_approved = 0))");
    }
}
