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
    private  JTextField selectText;
    private  JTextField deleteText;
    private  JTextField projectText;

    Object[] columnNames = {"LaunchID", "Is Approved", "Launch System", "Satellite ID", "Agency ID", "Scheduled Date"};

    // Create and show GUI
    public SatUI(SatDB db) {
        this.db = db;
        // Login login = new Login();
        // login.showFrame();
        JFrame frame = new JFrame("main ui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel insert = InsertPanel("Insert");
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

    private JPanel DivisionPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Division");
        JButton button = new JButton("DIVISION");
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
        JLabel filler = new JLabel("Nested Aggregation");
        JButton button = new JButton("NESTED");
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
        JLabel filler = new JLabel("Aggregation");
        JButton button = new JButton("AGGREGATION");
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
        JLabel filler = new JLabel("Join");
        JButton button = new JButton("JOIN");
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
        JLabel filler = new JLabel("Project");
        JButton button = new JButton("PROJECTION");
        projectText = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(projectText);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String field = projectText.getText();
                resultsTable = db.projectFromOrbit(field);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel SelectPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Select");
        JButton button = new JButton("SELECT");
        selectText = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(selectText);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String orbitType = selectText.getText();

                resultsTable = db.selectSatellite(orbitType);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel UpdatePanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Update");
        updateText = new JTextField(10);
        updateText2 = new JTextField(10);
        JButton button = new JButton("UPDATE");
        filler.setHorizontalAlignment(JLabel.CENTER);

        panel.add(filler);
        panel.add(updateText);
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
    protected JPanel InsertPanel(String text) {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel(text);
        insertText = new JTextField(10);
        insertText2  = new JTextField(10);
        insertText3 = new JTextField(10);
        JButton button = new JButton("INSERT");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);
        panel.add(insertText);
        panel.add(insertText2);
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
        JLabel filler = new JLabel("Delete");
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
