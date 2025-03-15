package Client;

// Importing necessary Swing components for creating the GUI
import javax.swing.*;
// Importing AWT event package for handling mouse and keyboard events
import java.awt.event.*;
// Importing IO classes for handling PrintWriter
import java.io.IOException;
import java.io.PrintWriter;
// Importing networking package for socket communication
import java.net.Socket;

// Class to handle sending mouse and keyboard events to the server
class SendEvents implements KeyListener, MouseMotionListener, MouseListener {
	// Private member variables for socket, panel, PrintWriter, width, and height
	private Socket clientSocket = null;
	private JPanel clientPanel = null;
	private PrintWriter commandWriter = null;
	String screenWidth = "", screenHeight = "";
	double widthScale;
	double heightScale;

	// Constructor to initialize socket, panel, width, and height
	SendEvents(Socket clientSocket, JPanel clientPanel, String screenWidth, String screenHeight) {
		this.clientSocket = clientSocket;
		this.clientPanel = clientPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		widthScale = Double.parseDouble(screenWidth.trim());
		heightScale = Double.parseDouble(screenHeight.trim());

		// Associate event listeners to the panel
		clientPanel.addKeyListener(this);
		clientPanel.addMouseListener(this);
		clientPanel.addMouseMotionListener(this);

		try {
			// Prepare PrintWriter which will be used to send commands to the client
			commandWriter = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Handle mouse dragged events (No implementation)
	public void mouseDragged(MouseEvent e) {
	}

	// Handle mouse moved events
	public void mouseMoved(MouseEvent e) {
		// Calculate the scale for x and y coordinates
		double xScale = widthScale / clientPanel.getWidth();
		double yScale = heightScale / clientPanel.getHeight();
		// Send the MOVE_MOUSE command and scaled coordinates to the server
		commandWriter.println(Commands.MOVE_MOUSE.getAbbrev());
		commandWriter.println((int) (e.getX() * xScale));
		commandWriter.println((int) (e.getY() * yScale));
		commandWriter.flush();
	}

	// Handle mouse clicked events (No implementation)
	public void mouseClicked(MouseEvent e) {
	}

	// Handle mouse pressed events
	public void mousePressed(MouseEvent e) {
		// Send the PRESS_MOUSE command and the button pressed to the server
		commandWriter.println(Commands.PRESS_MOUSE.getAbbrev());
		int button = e.getButton();
		int buttonCode = 16; // Default button code for left button
		if (button == MouseEvent.BUTTON3) {
			buttonCode = 4; // Button code for right button
		}
		commandWriter.println(buttonCode);
		commandWriter.flush();
	}

	// Handle mouse released events
	public void mouseReleased(MouseEvent e) {
		// Send the RELEASE_MOUSE command and the button released to the server
		commandWriter.println(Commands.RELEASE_MOUSE.getAbbrev());
		int button = e.getButton();
		int buttonCode = 16; // Default button code for left button
		if (button == MouseEvent.BUTTON3) {
			buttonCode = 4; // Button code for right button
		}
		commandWriter.println(buttonCode);
		commandWriter.flush();
	}

	// Handle mouse entered events (No implementation)
	public void mouseEntered(MouseEvent e) {
	}

	// Handle mouse exited events (No implementation)
	public void mouseExited(MouseEvent e) {
	}

	// Handle key typed events (No implementation)
	public void keyTyped(KeyEvent e) {
	}

	// Handle key pressed events
	public void keyPressed(KeyEvent e) {
		// Send the PRESS_KEY command and the key code to the server
		commandWriter.println(Commands.PRESS_KEY.getAbbrev());
		commandWriter.println(e.getKeyCode());
		commandWriter.flush();
	}

	// Handle key released events
	public void keyReleased(KeyEvent e) {
		// Send the RELEASE_KEY command and the key code to the server
		commandWriter.println(Commands.RELEASE_KEY.getAbbrev());
		commandWriter.println(e.getKeyCode());
		commandWriter.flush();
	}
}
