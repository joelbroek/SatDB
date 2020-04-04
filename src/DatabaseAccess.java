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
            if (conn != null) {
                conn.close();
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("attempting to get connection");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu", "ora_jbroek", "a50121862");
            System.out.println("connection established");
            conn.setAutoCommit(false);
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            satDB.displayError(e.getMessage());
        }
    }

    public void initialise() throws Exception {
        try {
            executeScript("SatDB_delete_database.sql");
            executeScript("SatDB_create_database.sql");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error occurred at initialise");
            throw e;
        }
//        disconnect();
    }


    public boolean login(String username, String password) {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu", username, password);
            conn.setAutoCommit(false);
//            System.out.println("Connected to database\n");
            return true;
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
            return false;
        }
    }

    public void disconnectAndResetDatabase() {
        try {
            reset();
            disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void reset() {
        try {
            executeScript("SatDB_delete_database.sql");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void disconnect() throws Exception{
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred at disconnect");
            throw e;
        }
    }

    // executes a .sql script file. Assumes statements are semicolon-separated
    private void executeScript(String filePath) throws Exception {
        StringBuffer buff = new StringBuffer();
        System.out.println("running script: " + filePath);

        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(reader);
            String str = "";
            while ((str = buffReader.readLine()) != null) {
                buff.append(str);
            }

            String[] instructions = buff.toString().split(";");
            Statement stmt = conn.createStatement();
            int numInstrs = instructions.length;
            for (String instr : instructions) {
//                System.out.println("UPDATE: " + instr);
                stmt.executeUpdate(instr);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Performs SQL 'UPDATE' statements
    public boolean performUpdate(String sqlString) {
        Statement stmt = null;
        try {
            System.out.println("UPDATE: " + sqlString);
            System.out.println(conn.isClosed());
            stmt = conn.createStatement();
            System.out.println("created statement");
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
    public JTable performQuery(String sqlString) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            System.out.println("performed query: " + sqlString);
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
            System.out.println("nCol: " + numCols);
            Vector<String> colNames = new Vector<>();
            Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
            for (int i = 1; i <= numCols; i++) {
                System.out.println("colName: " + metaData.getColumnName(i));
                colNames.add(metaData.getColumnName(i));
            }
            System.out.println(colNames.toString());
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
        System.out.println("UPDATE: DELETE FROM satellite(id, name, orbit_id, orbit_type, constellation) WHERE id = " + satelliteID);
        if (performUpdate("DELETE FROM satellite WHERE id = " + satelliteID)) {
            System.out.println("successfully deleted");
            return performQuery("SELECT id FROM satellite");
        } else {
            satDB.displayError("Failed to delete satellite");
            return new JTable(1,1);
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


    }
