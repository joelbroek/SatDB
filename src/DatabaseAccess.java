import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.Reader;
import java.sql.*;
import java.util.Vector;


public class DatabaseAccess {
    SatDB satDB = null;
    Connection conn = null;
    private int launchRequestNumber;

    public DatabaseAccess(SatDB caller) {
        // TODO - constructor
        satDB = caller;

    }

    // outdated
    public void initialise() throws Exception {
        // TODO - connects to database
        try {
            launchRequestNumber = 20;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // is this the right URL?
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu");

        } catch (Exception e) {
            System.out.println("Error occurred at initialise");
            throw e;
        }
        disconnect();
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
            ;
        }
    }

    private void reset() {
        try {
            executeScript("SatDB_delete_database.sql");
        } catch (Exception e) {
            ;
        }
    }


    private void disconnect() throws Exception{
        // TODO
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred at disconnect");
            throw e;
        }
    }

    private void executeScript(String filePath) throws Exception {
        Reader reader = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw e;
        }
    }


    // Performs SQL 'UPDATE' statemxents
    public boolean performUpdate(String sqlString) {
        Statement stmt = null;
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
    public JTable performQuery(String sqlString) {
        Statement stmt = null;
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
            for (int i = 1; i < numCols; i++) {
                colNames.add(metaData.getColumnName(i));
            }
            while (rs.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 1; i < numCols; i++) {
                    v.add(rs.getObject(i));
                }
                tableData.add(v);
            }
            return new DefaultTableModel();
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
            return null;
        }

    }


    public JTable insertLaunchRequest(String launchSystem, int satID, String agencyID, String date) {
        int launchID = launchRequestNumber;
        launchRequestNumber++;
        return performQuery("INSERT INTO LaunchRequest VALUES "
                + launchID
                +", false, "
                + launchSystem
                + ", " + satID
                + agencyID + ", "
                + date
                );
    }
    // Deletes satellite with given ID, then retrieves all Satellites
    public JTable deleteSatellite(int satelliteID) {
        if (performUpdate("DELETE FROM Satellite WHERE id = " + satelliteID)) {
            return performQuery("SELECT id FROM Satellite");
        } else {
            satDB.displayError("Failed to delete satellite");
            return new JTable();
        }
    }

    public JTable updateConstellation(String purpose, String name) {
        if (performUpdate("UPDATE constellation SET purpose = " + purpose + " WHERE name = " + name)) {
            return performQuery("SELECT name FROM constellation");
        } else {
            satDB.displayError("Failed to update constellation");
            return new JTable();
        }
    }

    public JTable selectSatellite(String orbitType) {
        return performQuery("SELECT id FROM satellite WHERE orbit_type = " + orbitType);
    }

    public JTable projectFromOrbit(String field) {
        return performQuery("SELECT orbit_id, " + field + " FROM orbit");
    }

    public JTable joinQuery() {
        return performQuery("SELECT Satellite.id, Constellation.purpose FROM Satellite " +
                "INNER JOIN Constellation ON Satellite.constellation = Constellation.name");
    }

    public JTable aggregationQuery() {
        return performQuery("SELECT MAX(eccentricity) AS HighestEccentricity FROM Orbit");
    }

    public JTable nestedAggregationQuery() {
        return performQuery("SELECT Avg(count (isApproved) FROM LaunchRequest Group by sat_id");
    }
}
