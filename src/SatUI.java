import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SatUI extends JPanel {
    private JScrollPane resultsPane;
    private JTable resultsTable;
    private SatDB db;
    private JTextField insertText;
    private JTextField insertText2;
    private JTextField insertText3;
    private JTextField updateText;
    private JTextField updateText2;
    private  JComboBox selectText;
    private  JTextField deleteText;
    private  JComboBox projectText;
    private JTextArea ta;
    private JScrollPane textPane;

    // Create and show GUI
    public SatUI(SatDB db) {
        this.db = db;
        // Login login = new Login();
        // login.showFrame();
        JFrame frame = new JFrame("main ui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 600);

        JPanel insert = InsertPanel();
        JPanel delete = DeletePanel();
        JPanel update = UpdatePanel();
        JPanel select = SelectPanel();
        JPanel project = ProjectPanel();
        JPanel join = JoinPanel();
        JPanel aggregate = AggPanel();
        JPanel nested = NestedPanel();
        JPanel division = DivisionPanel();
        resultsTable = new JTable(5,5);
        resultsPane = new JScrollPane(resultsTable);
        ta = new JTextArea(10, 40);
        ta.setLineWrap(true);
        textPane = new JScrollPane(ta);
        frame.add(textPane, BorderLayout.EAST);
        frame.add(insert);
        frame.add(delete);
        frame.add(update);
        frame.add(select);
        frame.add(project);
        frame.add(join);
        frame.add(aggregate);
        frame.add(nested);
        frame.add(division);
        frame.add(resultsPane, BorderLayout.PAGE_END);

        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }

    public void displayMessage(String msg) {
        ta.append(msg + "\n");
    }


    public void displayError(String errorMsg) {
        ta.append("ERROR OCCURRED: App may require restart\n");
        ta.append("Error Message::    " + errorMsg + "\n");

    }
    private JPanel DivisionPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Find the best space agencies!");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("FIND");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
                resultsTable = db.divisionQuery();
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel NestedPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Find Average Agency Launches");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("FIND");
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resultsTable = db.nestedAggregationQuery();
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel AggPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Find Orbit with Maximum Eccentricity");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("FIND");
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resultsTable = db.aggregationQuery();
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel JoinPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Find Satellite Purposes");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("FIND");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resultsTable = db.joinQuery();
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel ProjectPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Get Orbit Information");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("GET");
        String[] options = new String[] {"Type", "Longitude", "Eccentricity", "Axis"};
        projectText = new JComboBox<>(options);
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(projectText);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String field = (String) projectText.getSelectedItem();;
                resultsTable = db.projectFromOrbit(field);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel SelectPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Select Satellite Orbit");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton button = new JButton("SELECT");
        String[] options = new String[] {"High", "Middle", "Low"};
        selectText = new JComboBox<>(options);
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(selectText);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String orbitType = (String) selectText.getSelectedItem();

                resultsTable = db.selectSatellite(orbitType);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel UpdatePanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Update Constellation Purpose");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        updateText = new JTextField(10);
        updateText2 = new JTextField(10);
        JLabel label1 = new JLabel("Purpose:");
        JLabel label2 = new JLabel("Name:");
        JButton button = new JButton("UPDATE");
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(label1);
        panel.add(updateText);
        panel.add(label2);
        panel.add(updateText2);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String purpose = updateText.getText();
                String name = updateText2.getText();
                resultsTable = db.updateConstellation(purpose, name);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }


    // INSERT LAUNCH_REQUEST
    protected JPanel InsertPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Make Launch Request");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        JLabel label1 = new JLabel("Launch System:");
        JLabel label2 = new JLabel("Satellite ID:");
        JLabel label3 = new JLabel("Date of Launch:");
        insertText = new JTextField(10);
        insertText2  = new JTextField(10);
        insertText3 = new JTextField(10);
        JButton button = new JButton("REQUEST");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);
        panel.add(label1);
        panel.add(insertText);
        panel.add(label2);
        panel.add(insertText2);
        panel.add(label3);
        panel.add(insertText3);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // perform insert
                String launchSys = insertText.getText();
                int satId = Integer.parseInt(insertText2.getText());
                String agency = "1";
                String ld = insertText3.getText();

                resultsTable = db.insertLaunchRequest(launchSys, satId , agency, ld);

                resultsPane.setViewportView(resultsTable);
            }

        });
        return panel;
    }


    // DELETE SATELLITE
    protected JPanel DeletePanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Delete Satellite (Id)");
        filler.setFont(new Font("Ariel", Font.BOLD, 14));
        deleteText = new JTextField(20);
        JButton button = new JButton("DELETE");
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(deleteText);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int id = Integer.parseInt(deleteText.getText());

                resultsTable = db.deleteSatellite(id);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }
}
