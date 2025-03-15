package Client;

// Importing necessary Swing components for creating the GUI
import javax.swing.*;
// Importing networking package for socket communication
import java.net.Socket;

// Class to start the client application
public class Start {
	// Default port number for the server connection
	public static String defaultPort = "1235";
	// Variable to hold the port number
	public static String port;

	// Main method to start the client application
	public static void main(String[] args) {
		// Taking a server IP address from the user using a dialog box
		String serverIp = JOptionPane.showInputDialog("Please enter server IP");
		// Create a new Start object and call the initialize method with the entered IP and default port
		new Start().initialize(serverIp, Integer.parseInt(defaultPort));
	}

	// Method to initialize the connection to the server
	public void initialize(String serverIp, int port) {
		try {
			// Create a new socket connection to the specified IP and port
			Socket clientSocket = new Socket(serverIp, port);
			System.out.println("Connecting to the Server");
			// Create an Authenticate object to handle user authentication
			Authenticate authFrame = new Authenticate(clientSocket);
			// Set the size, location, and visibility of the Authenticate frame
			authFrame.setSize(300, 80);
			authFrame.setLocation(500, 300);
			authFrame.setVisible(true);
		} catch (Exception ex) {
			// Print the stack trace if an exception occurs
			ex.printStackTrace();
		}
	}
}
