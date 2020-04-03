import javax.swing.*;

import ui.*;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn;

import java.awt.event.ActionEvent;

public class SatUI extends JPanel {

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
        JPanel result = ResultPanel();

        frame.add(insert);
        frame.add(delete);
        frame.add(update);
        frame.add(select);
        frame.add(project);
        frame.add(join);
        frame.add(aggregate);
        frame.add(nested);
        frame.add(division);
        frame.add(result, BorderLayout.PAGE_END);

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

    public void displayError(String errorMsg) {
        // called by self or SatDB. displays error in GUI
    }
}
