import javax.swing.*;

public class SatDB{
    private DatabaseAccess dba;
    private SatUI satUI;
    public static void main(String[] args) {
        SatDB sdb = new SatDB();
        sdb.start();
    }

//    @Override
    public void start() {
        try {
            dba = new DatabaseAccess(this);
            initialiseDatabase();
            createAndShowGUI();
        } catch (Exception e) {
            System.out.println("Error occurred on start");
            System.out.println(e.getMessage());
//            exit();
        }
    }

    private void initialiseDatabase() {
        try {
            dba.initialise();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAndShowGUI() {
        JFrame f = new JFrame("A JFrame");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(250, 250);
        f.setLocation(300,200);
        SatUI satUI = new SatUI(this);
        f.setContentPane(satUI);
        satUI.setOpaque(true);
        f.pack();
        f.setVisible(false);
    }

    public void displayError(String errorMsg) {
       // satUI.displayError(errorMsg);
        System.out.println("Error Occurred:    " + errorMsg);
    }

    // THE FOLLOWING FUNCTIONS are called by SatUI, allow use of Database functionality


    // Performs SQL statement of the form "UPDATE ..."
    // returns TRUE if successful, otherwise returns FALSE and calls displayError() with error message.
    public boolean performUpdate(String sqlString) {
        return dba.performUpdate(sqlString);
    }

    // Performs SQL queries of the form "SELECT ..."
    // returns JTable with results. If error occurs, returns empty table, and calls displayError() with error message.
    public JTable performQuery(String sqlString) {
        return dba.performQuery(sqlString);
    }
    // Attempts to login into database with given username/password.
    // Returns TRUE if successful, FALSE otherwise
    public boolean login(String username, String password) {
        if (dba.login(username, password)){
            initialiseDatabase();
            return true;
        } else {
            return false;
        }
    }


    // Terminates database connection.
    public void disconnectAndResetDatabase() {
        // called by SatUI, close database connection
        try {
            dba.disconnectAndResetDatabase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public JTable insertLaunchRequest(String launchSystem, int satID, String agencyID, String date) {
        return dba.insertLaunchRequest(launchSystem, satID, agencyID, date);
    }
    public JTable deleteSatellite(int satelliteID) {
        return dba.deleteSatellite(satelliteID);
    }
    public JTable updateConstellation(String purpose, String name) {
        return dba.updateConstellation(purpose, name);
    }
    public JTable selectSatellite(String orbitType) {
        return dba.selectSatellite(orbitType);
    }
    public JTable projectFromOrbit(String field) {
        return dba.projectFromOrbit(field);
    }
    public JTable joinQuery() {
        return dba.joinQuery();
    }
    public JTable aggregationQuery() {
        return dba.aggregationQuery();
    }
    public JTable nestedAggregationQuery() {
        return dba.nestedAggregationQuery();
    }

    }