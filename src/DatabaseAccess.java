import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import src.model;

public class DatabaseAccess {
    SatDB satDB = null;
    Connection conn = null;

    public DatabaseAccess(SatDB caller) {
        // TODO - constructor
        satDB = caller;

    }

    // outdated
    public void initialise() throws Exception {
        // TODO - connects to database
        try {
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

    public void disconnect() throws Exception{
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


    // Performs SQL 'UPDATE' statements
    public boolean performUpdate(String sqlString) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlString);
            return true;
        } catch (SQLException e) {
            satDB.displayError(e.getMessage());
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


    //queries for project
    // return list of existing users
    public ArrayList<String> queryUser(){
      ArrayList<String> result = new ArrayList<String>();

      try{
            Statement stmt = connection.createStatement():
            ResultSet rs = stmt.executeQuery("SELECT USER_USERNAME FROM USER");

            while (rs.next()){
              result.add(rs.getString("USER_USERNAME"));
            }
            rs.close();
            stmt.close();
          }
          catch(SQLException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
          }
          return result;
      }

    //return a map of satellite and constellation

    public HashMap<Integer,String> satelliteInfo(){
      HashMap<Integer,String> result = new Hashmap<>();

      try{
          Statement stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT * FROM Satelltite");

          while (rs.next()){
            result.put(rs.getInt("id"), rs.getString("constellation"));
          }

          rs.close();
          stmt.close();
      }catch (SQLException e){
        System.out.printIn(EXCEPTION_TAG + " " + e.getMessage());
      }
      return result;
    }



}
