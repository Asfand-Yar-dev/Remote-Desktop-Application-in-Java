package Server;

// Importing AWT components for handling graphics and screen operations
import java.awt.*;
// Importing IO classes for handling data input and output streams
import java.io.DataInputStream;
import java.io.DataOutputStream;
// Importing networking package for socket communication
import java.net.ServerSocket;
import java.net.Socket;

// Class to initialize the server connection and handle client authentication
public class InitConnection {
	// Private member variables for server socket, input/output streams, and screen dimensions
	private ServerSocket serverSocket = null;
	private DataInputStream passwordInputStream = null;
	private DataOutputStream verificationOutputStream = null;
	private String screenWidth = "";
	private String screenHeight = "";

	// Constructor to initialize the server connection with the specified port and password
	InitConnection(int port, String password) {
		Robot screenRobot = null;
		Rectangle screenRect = null;
		try {
			// Awaiting connection from client
			System.out.println("Awaiting Connection from Client");
			serverSocket = new ServerSocket(port);

			// Get the default screen device and dimensions
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

			Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = "" + screenDimension.getWidth();
			screenHeight = "" + screenDimension.getHeight();
			screenRect = new Rectangle(screenDimension);
			screenRobot = new Robot(graphicsDevice);

			// Draw the server GUI (implementation not provided)
			drawGUI();

			// Continuously listen for client connections
			while (true) {
				// Accept a client connection
				Socket clientSocket = serverSocket.accept();
				passwordInputStream = new DataInputStream(clientSocket.getInputStream());
				verificationOutputStream = new DataOutputStream(clientSocket.getOutputStream());

				// Read the password sent by the client
				String receivedPassword = passwordInputStream.readUTF();

				// Verify the received password
				if (receivedPassword.equals(password)) {
					// If the password is valid, send the screen dimensions and start sending/receiving events
					verificationOutputStream.writeUTF("valid");
					verificationOutputStream.writeUTF(screenWidth);
					verificationOutputStream.writeUTF(screenHeight);
					new SendScreen(clientSocket, screenRobot, screenRect);
					new ReceiveEvents(clientSocket, screenRobot);
				} else {
					// If the password is invalid, send an invalid response
					verificationOutputStream.writeUTF("Invalid");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Method to draw the server GUI (implementation not provided)
	private void drawGUI() {
	}
}
