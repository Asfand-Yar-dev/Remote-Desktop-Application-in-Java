package Client;

// Importing necessary Swing components for creating the GUI
import javax.swing.*;
// Importing AWT components for layout management and event handling
import java.awt.*;
// Importing AWT event package for handling action events
import java.awt.event.*;
// Importing IO classes for handling data input and output streams
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
// Importing networking package for socket communication
import java.net.Socket;

// Class for authenticating the user by checking the password with the server
class Authenticate extends JFrame implements ActionListener {
	// Socket for communication with the server
	private Socket clientSocket = null;
	// Output stream to send data to the server
	DataOutputStream passwordCheck = null;
	// Input stream to receive data from the server
	DataInputStream serverResponse = null;
	// String to hold the server's response
	String verificationResponse = "";
	// Button to submit the password
	JButton submitButton;
	// Panel to hold the form components
	JPanel panel;
	// Label for the password field
	JLabel passwordLabel, messageLabel;
	// Variables to hold screen dimensions
	String screenWidth = "", screenHeight = "";
	// Text field for the user to enter the password
	final JTextField passwordField;

	// Constructor to initialize the form components and set up the UI
	Authenticate(Socket clientSocket) {
		// Label for the password field
		passwordLabel = new JLabel();
		passwordLabel.setText("Password");

		// Text field for entering the password
		passwordField = new JTextField(15);

		// Assigning the passed socket to the class variable
		this.clientSocket = clientSocket;

		// Label to display messages
		messageLabel = new JLabel();
		messageLabel.setText("");

		// Setting the layout for the frame
		this.setLayout(new BorderLayout());

		// Button for submitting the password
		submitButton = new JButton("SUBMIT");

		// Panel with grid layout to hold the form components
		panel = new JPanel(new GridLayout(2, 1));
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(messageLabel);
		panel.add(submitButton);

		// Adding the panel to the frame
		add(panel, BorderLayout.CENTER);

		// Adding action listener to the submit button
		submitButton.addActionListener(this);

		// Setting the title for the frame
		setTitle("LOGIN FORM");
	}

	// Method to handle the submit button click event
	public void actionPerformed(ActionEvent event) {
		// Getting the password entered by the user
		String password = passwordField.getText();

		try {
			// Initializing the output and input streams for communication with the server
			passwordCheck = new DataOutputStream(clientSocket.getOutputStream());
			serverResponse = new DataInputStream(clientSocket.getInputStream());

			// Sending the password to the server
			passwordCheck.writeUTF(password);

			// Reading the server's response
			verificationResponse = serverResponse.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// If the server response is "valid", proceed to create the main frame
		if (verificationResponse.equals("valid")) {
			try {
				// Reading screen dimensions from the server
				screenWidth = serverResponse.readUTF();
				screenHeight = serverResponse.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Creating the main frame for the remote desktop application
			CreateFrame mainFrame = new CreateFrame(clientSocket, screenWidth, screenHeight);
			// Closing the authentication frame
			dispose();
		} else {
			// If the password is incorrect, display an error message
			System.out.println("Enter a valid password");
			JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
			// Closing the authentication frame
			dispose();
		}
	}
}
