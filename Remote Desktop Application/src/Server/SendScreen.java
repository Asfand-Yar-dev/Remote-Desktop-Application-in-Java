package Server;

// Importing necessary classes for image processing
import javax.imageio.ImageIO;
// Importing AWT components for handling graphics and screen operations
import java.awt.*;
// Importing classes for handling buffered images
import java.awt.image.BufferedImage;
// Importing IO classes for handling output streams and exceptions
import java.io.IOException;
import java.io.OutputStream;
// Importing networking package for socket communication
import java.net.Socket;

// Class to capture and send screen images to the client
class SendScreen extends Thread {
	// Private member variables for socket, Robot instance, screen rectangle, and output stream
	private Socket clientSocket = null;
	private Robot screenRobot = null;
	private Rectangle screenRect = null;
	private boolean continueSending = true;
	private OutputStream outputStream = null;

	// Constructor to initialize socket, Robot instance, and screen rectangle
	public SendScreen(Socket clientSocket, Robot screenRobot, Rectangle screenRect) {
		this.clientSocket = clientSocket;
		this.screenRobot = screenRobot;
		this.screenRect = screenRect;
		// Start the thread
		start();
	}

	// Run method to handle the capturing and sending of screen images
	public void run() {
		try {
			// Initialize the output stream to send images to the client
			outputStream = clientSocket.getOutputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		while (continueSending) {
			// Capture the screen image
			BufferedImage screenCapture = screenRobot.createScreenCapture(screenRect);

			try {
				// Write the captured screen image to the output stream in JPEG format
				ImageIO.write(screenCapture, "jpeg", outputStream);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			try {
				// Sleep for a short period before capturing the next screen image
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
