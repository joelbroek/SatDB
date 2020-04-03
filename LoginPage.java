import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import LoginPageDelegate;


public class LoginPage extends JFrame implements ActionListener {
	private static final int TEXT_FIELD_WIDTH = 10;
	private static final int MAX_LOGIN_ATTEMPTS = 3;

	private int loginAttempts;

	private JTextField usernameField;
	private JPasswordField passwordField;

	private LoginPageDelegate delegate;

	public LoginPage() {
		super("User Login");
	}

	public void showFrame(LoginPageDelegate delegate) {
		this.delegate = delegate;
		loginAttempts = 0;

		JLabel usernameLabel = new JLabel("Enter username: ");
		JLabel passwordLabel = new JLabel("Enter password: ");

		usernameField = new JTextField(TEXT_FIELD_WIDTH);
		passwordField = new JPasswordField(TEXT_FIELD_WIDTH);
		passwordField.setEchoChar('*');

		JButton loginButton = new JButton("Log In");

		JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		contentPane.add(usernameLabel);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		contentPane.add(loginButton);

		loginButton.addActionListener(this);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
  );

		this.pack();

		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		 this.setVisible(true);

		usernameField.requestFocus();
	}

	public void handleLoginFailed() {
		loginAttempts++;
		passwordField.setText("");
	}

	public boolean hasReachedMaxLoginAttempts() {
		return (loginAttempts >= MAX_LOGIN_ATTEMPTS);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			delegate.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));
		} catch (IOException ex){
			System.out.println("IO Exception");

		}
	}
}
