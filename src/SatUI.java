import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class SatUI extends JPanel {
    JScrollPane resultsPane;
    JTable resultsTable;
    Object[] columnNames = {"LaunchID", "Is Approved", "Launch System", "Satellite ID", "Agency ID", "Scheduled Date"};

    // Create and show GUI
    public SatUI() {
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
        System.out.println("got here");
        // MainUI ui = new MainUI();
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
                resultsTable = new JTable(12,12);
                resultsPane.setViewportView(resultsTable);
            }
        });
        return panel;
    }

    private JPanel NestedPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Nested Aggregation");
        JButton button = new JButton("NESTED");
        JTextField textField = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
            }
        });
        return panel;
    }

    private JPanel AggPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Aggregation");
        JButton button = new JButton("AGGREGATION");
        JTextField textField = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
            }
        });
        return panel;
    }

    private JPanel JoinPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Join");
        JButton button = new JButton("JOIN");
        JTextField textField = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
            }
        });
        return panel;
    }

    private JPanel ProjectPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Project");
        JButton button = new JButton("PROJECTION");
        JTextField textField = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform projection
            }
        });
        return panel;
    }

    private JPanel SelectPanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Select");
        JButton button = new JButton("SELECT");
        JTextField textField = new JTextField(20);
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
            }
        });
        return panel;
    }

    private JPanel UpdatePanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Update");
        JTextField textField = new JTextField(20);
        panel.add(textField);
        JButton button = new JButton("UPDATE");
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert


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

    protected static JPanel InsertPanel(String text) {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel(text);
        JTextField textField = new JTextField(20);
        JButton button = new JButton("INSERT");
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform insert
            System.out.print("insert launchSystem");
            String ls = textField.getText();
            System.out.print("insert satellite id");
            String sd = parseInt(textField.getText());
            System.out.print("insert Agency ID");
            String ad = parseInt(textField.getText());
            System.out.print("insert launch date");
            String ld = textField.getText();

            database.insertLaunchRequest(ls, sd,ad, ld);

            PreparedStatement ps;
            DefaultTableModel model = new DefaultTableModel(0,6);
            model.addRow(columnNames);

            try{
              ps = PreparedStatement("Select ID, Is_Approved, Launch System, Satellite ID, Agency Id, Scheduled Date");
              ResultSet rs;
              rs = ps.executeQuery();
              while(rs.next()) {
                System.out.println ("Next result");
                String id = rs.getString(1);
                String is_approved = rs.getString(2);
                String launch_system = rs.getString(3);
                String sat_id = rs.getString(4);
                String agency_id = rs.getString(5);
                String scheduled_date = rs.getString(6);
                model.addRow(new Object[]{rd, is_approved, launch_system, sat_id, agency_id, scheduled_date});
              }
              ps.close();
            }
            catch(SQLException ex)
                    {
                        System.out.println("Message: "+ex.getMessage());
                    }
                    resultsTable.setModel(model);

            }

        });
        return panel;
    }

    protected static JPanel DeletePanel() {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Delete");
        JTextField textField = new JTextField(20);
        panel.add(textField);
        JButton button = new JButton("DELETE");
        filler.setHorizontalAlignment(JLabel.CENTER);
        // panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        panel.add(textField);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // try to perform delete
            }
        });
        return panel;
    }



}
