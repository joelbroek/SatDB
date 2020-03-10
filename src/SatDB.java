import javax.swing.*;

public class SatDB {

    public static void main(String[] args) {

        initialiseDatabase();
        createAndShowGUI();


    }

    private static void initialiseDatabase() {
        //
    }
    private static void createAndShowGUI() {
        //
        JFrame f = new JFrame("A JFrame");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250, 250);
        f.setLocation(300,200);
        SatUI newContentPane = new SatUI();
        f.setContentPane(newContentPane);
        newContentPane.setOpaque(true);
        f.pack();
        f.setVisible(true);
    }

}