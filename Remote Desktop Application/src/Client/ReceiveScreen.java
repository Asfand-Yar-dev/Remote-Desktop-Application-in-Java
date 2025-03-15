package Client;

// Importing necessary classes for image processing and UI components
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// Class to receive and display screen images from the server
class ReceiveScreen extends Thread {
	// Private member variables for input stream, client panel, image data, and loop control
	private InputStream inputStream = null;
	private JPanel clientPanel = null;
	private boolean continueLoop = true;
	private Image receivedImage = null;

	// Constructor to initialize input stream and client panel
	public ReceiveScreen(InputStream inputStream, JPanel clientPanel) {
		this.inputStream = inputStream;
		this.clientPanel = clientPanel;
		start(); // Start the thread upon construction
	}

	// Run method to handle receiving and displaying screen images
	public void run() {
		try {
			// Continuously read and display screenshots from the server
			while (continueLoop) {
				// Read image data byte array
				byte[] imageBytes = new byte[1024 * 1024];
				int bytesRead = 0;
				// Continue reading until image end marker is found
				do {
					bytesRead += inputStream.read(imageBytes, bytesRead, imageBytes.length - bytesRead);
				} while (!(bytesRead > 4 && imageBytes[bytesRead - 2] == (byte) -1 && imageBytes[bytesRead - 1] == (byte) -39));

				// Convert byte array to Image
				receivedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
				// Scale the image to fit the client panel size
				receivedImage = receivedImage.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);

				// Get graphics context of client panel and draw the received image
				Graphics graphics = clientPanel.getGraphics();
				graphics.drawImage(receivedImage, 0, 0, clientPanel.getWidth(), clientPanel.getHeight(), clientPanel);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
