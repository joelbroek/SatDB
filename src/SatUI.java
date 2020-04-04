import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

public class SatUI extends JPanel {
    JScrollPane resultsPane;
    JTable resultsTable;
    SatDB db;
    JTextField insertText;
    JTextField insertText2;
    JTextField insertText3;
    JTextField updateText;
    JTextField updateText2;
    JTextField selectText;
    JTextField deleteText;
    JTextField projectText;

    Object[] columnNames = {"LaunchID", "Is Approved", "Launch System", "Satellite ID", "Agency ID", "Scheduled Date"};
   // Private Connection conn;

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
//        JScrollPane result = new JScrollPane(resultsTable);
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
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
                resultsTable = new JTable(12, 12);
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
            }
        });
        return panel;
    }

    private JPanel JoinPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Join");
        JButton button = new JButton("JOIN");
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resultsTable = db.joinQuery();
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
                String name = insertText2.getText();
                resultsTable = db.updateConstellation(purpose, name);

                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel ResultPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Results:");
        JTable table = new JTable(5,5);
        panel.add(label);
        panel.add(table);
        return panel;
    }

    protected JPanel InsertPanel(String text) {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel(text);
        insertText = new JTextField(10);
        insertText2  = new JTextField(10);
        insertText3 = new JTextField(10);
        JButton button = new JButton("INSERT");
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(insertText);
        panel.add(insertText2);
        panel.add(insertText3);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // perform insert
            System.out.print("insert launchSystem");
            String launchSys = insertText.getText();
            System.out.print("insert satellite id");
            int satId = Integer.parseInt(insertText2.getText());
            System.out.print("insert Agency ID");
            String agency = "agency 1";
            System.out.print("insert launch date");
            String ld = insertText2.getText();

            resultsTable = db.insertLaunchRequest(launchSys, satId , agency, ld);

            resultsPane.setViewportView(resultsTable);

            // Commented out bc it was causing compile errors
//            PreparedStatement ps;
//            DefaultTableModel model = new DefaultTableModel(0,6);
//            model.addRow(columnNames);
//
//            try{
//              ps = con.prepareStatement("Select ID, Is_Approved, Launch System, Satellite ID, Agency Id, Scheduled Date");
//              ResultSet rs;
//              rs = ps.executeQuery();
//              while(rs.next()) {
//                System.out.println ("Next result");
//                String id = rs.getString(1);
//                String is_approved = rs.getString(2);
//                String launch_system = rs.getString(3);
//                String sat_id = rs.getString(4);
//                String agency_id = rs.getString(5);
//                String scheduled_date = rs.getString(6);
//                model.addRow(new Object[]{rd, is_approved, launch_system, sat_id, agency_id, scheduled_date});
//              }
//              ps.close();
//            }
//            catch(SQLException ex)
//                    {
//                        System.out.println("Message: "+ex.getMessage());
//                    }
//                    resultsTable.setModel(model);
//
           }

        });
        return panel;
    }

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
            }
        });
        return panel;
    }
}
