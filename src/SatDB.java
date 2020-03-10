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
            dba = new DatabaseAccess();
            initialiseDatabase();
            createAndShowGUI();
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
    }

    private void initialiseDatabase() {
        //
        dba.initialise();
    }
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

    public void disconnectDatabase() {
        // called by SatUI, close database connection
        dba.disconnect();
    }
}