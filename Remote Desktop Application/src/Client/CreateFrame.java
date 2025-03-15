package Client;

// Importing necessary Swing components for creating the GUI
import javax.swing.*;
// Importing AWT components for layout management
import java.awt.*;
// Importing beans package for handling property events
import java.beans.PropertyVetoException;
// Importing IO classes for handling input streams
import java.io.IOException;
import java.io.InputStream;
// Importing networking package for socket communication
import java.net.Socket;

// Class to create the main frame for displaying the remote desktop screen
class CreateFrame extends Thread {
	// Variables to hold screen dimensions
	String screenWidth = "", screenHeight = "";
	// Main frame of the application
	private JFrame mainFrame = new JFrame();
	// Desktop pane to hold internal frames
	private JDesktopPane desktopPane = new JDesktopPane();
	// Socket for communication with the server
	private Socket clientSocket = null;
	// Internal frame to display the server screen
	private JInternalFrame internalFrame = new JInternalFrame("Server Screen", true, true, true);
	// Panel to hold the server screen
	private JPanel clientPanel = new JPanel();

	// Constructor to initialize the frame with screen dimensions and socket
	public CreateFrame(Socket clientSocket, String screenWidth, String screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.clientSocket = clientSocket;
		start();  // Start the thread
	}

	// Method to draw the GUI for each connected client
	public void drawGUI() {
		// Add the desktop pane to the main frame
		mainFrame.add(desktopPane, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the frame in maximized state
		mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);

		// Set up the internal frame
		internalFrame.setLayout(new BorderLayout());
		internalFrame.getContentPane().add(clientPanel, BorderLayout.CENTER);
		internalFrame.setSize(100, 100);
		desktopPane.add(internalFrame);

		try {
			// Initially show the internal frame maximized
			internalFrame.setMaximum(true);
		} catch (PropertyVetoException ex) {
			ex.printStackTrace();
		}

		// Allow the client panel to handle KeyListener events
		clientPanel.setFocusable(true);
		internalFrame.setVisible(true);
	}

	// Method to run the thread
	public void run() {
		// Input stream to read screenshots
		InputStream inputStream = null;
		// Start drawing the GUI
		drawGUI();

		try {
			// Initialize the input stream from the socket
			inputStream = clientSocket.getInputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Start receiving screenshots
		new ReceiveScreen(inputStream, clientPanel);
		// Start sending events to the server
		new SendEvents(clientSocket, clientPanel, screenWidth, screenHeight);
	}
}
