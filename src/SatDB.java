import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;

public class SatDB extends Application {
    DatabaseAccess dba;
    SatUI satUI;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            dba = new DatabaseAccess(this);
//            initialiseDatabase();
            createAndShowGUI();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    private void initialiseDatabase() throws Exception {
//        //
//        dba.initialise();
//    }


    private static void createAndShowGUI() {
        //
        JFrame f = new JFrame("A JFrame");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(250, 250);
        f.setLocation(300,200);
        SatUI satUI = new SatUI();
        f.setContentPane(satUI);
        satUI.setOpaque(true);
        f.pack();
        f.setVisible(true);
    }

    public void displayError(String errorMsg) {
        satUI.displayError(errorMsg);
    }

    // THE FOLLOWING FUNCTIONS are called by SatUI, allow use of Database functionality


    // Attempts to login into database with given username/password.
    // Returns TRUE if successful, FALSE otherwise
    public boolean login(String username, String password) {
        return dba.login(username, password);
    }


    // Terminates database connection.
    public void disconnectDatabase() {
        // called by SatUI, close database connection
        try {
            dba.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}