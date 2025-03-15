package Server;

// Importing necessary Swing components for creating the GUI
import javax.swing.*;
// Importing AWT components for handling graphics and layout
import java.awt.*;
// Importing AWT event classes for event handling
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to create a password setting GUI
public class SetPassword extends JFrame implements ActionListener {
	// Static variable for default port number
	static String port = "4907";
	// Components for the GUI
	JButton submitButton;
	JPanel mainPanel;
	JLabel passwordLabel;
	JTextField passwordField;
	String password;

	// Constructor to initialize the GUI components
	public SetPassword() {
		passwordLabel = new JLabel("Set Password");
		passwordField = new JTextField(15);

		// Setting layout for the frame
		this.setLayout(new BorderLayout());

		submitButton = new JButton("SUBMIT");

		// Creating a main panel with GridLayout
		mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);
		mainPanel.add(submitButton);

		// Adding the main panel to the frame
		add(mainPanel, BorderLayout.CENTER);
		submitButton.addActionListener(this);
		setTitle("Set Password to connect to the Client");
	}

	// ActionListener implementation to handle button click
	public void actionPerformed(ActionEvent e) {
		password = passwordField.getText();
		dispose(); // Close the frame after getting the password
		new InitConnection(Integer.parseInt(port), password); // Start server connection with provided password
	}

	// Getter method to retrieve the password
	public String getPassword() {
		return password;
	}

	// Main method to create and display the password setting frame
	public static void main(String[] args) {
		SetPassword passwordFrame = new SetPassword();
		passwordFrame.setSize(300, 80);
		passwordFrame.setLocation(500, 300);
		passwordFrame.setVisible(true);
	}
}
