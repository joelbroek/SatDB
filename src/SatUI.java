import javax.swing.*;
import ui.*;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn;

import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.FlowLayout;

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

        frame.setLayout(new FlowLayout());
        frame.setVisible(true);

        // MainUI ui = new MainUI();
    }

    private JPanel DivisionPanel() {
        return null;
    }

    private JPanel NestedPanel() {
        return null;
    }

    private JPanel AggPanel() {
        return null;
    }

    private JPanel JoinPanel() {
        return null;
    }

    private JPanel ProjectPanel() {
        return null;
    }

    private JPanel SelectPanel() {
        return null;
    }

    private JPanel UpdatePanel() {
        return null;
    }

    private JPanel ResultPanel() {
        JPanel panel = new JPanel();
        return panel;
    }

    protected static JPanel InsertPanel(String text) {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel(text);
        JTextField textField = new JTextField(20);
        panel.add(textField);
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
