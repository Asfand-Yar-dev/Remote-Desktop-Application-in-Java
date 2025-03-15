package Server;

// Importing AWT components for handling graphics and screen operations
import java.awt.*;
// Importing IO package for handling IO exceptions
import java.io.IOException;
// Importing networking package for socket communication
import java.net.Socket;
// Importing utility package for handling Scanner
import java.util.Scanner;

// Class to receive and process events from the client
class ReceiveEvents extends Thread {
	// Private member variables for socket, Robot instance, and a flag to control the loop
	private Socket clientSocket = null;
	private Robot screenRobot = null;
	private boolean continueReceiving = true;

	// Constructor to initialize socket and Robot instance
	public ReceiveEvents(Socket clientSocket, Robot screenRobot) {
		this.clientSocket = clientSocket;
		this.screenRobot = screenRobot;
		// Start the thread
		start();
	}

	// Run method to handle the receiving of events
	public void run() {
		Scanner commandScanner = null;
		try {
			// Initialize the scanner to read commands from the client socket's input stream
			commandScanner = new Scanner(clientSocket.getInputStream());
			while (continueReceiving) {
				// Receive commands and respond accordingly
				int command = commandScanner.nextInt();
				switch (command) {
					case -1: // Command to press the mouse button
						screenRobot.mousePress(commandScanner.nextInt());
						break;
					case -2: // Command to release the mouse button
						screenRobot.mouseRelease(commandScanner.nextInt());
						break;
					case -3: // Command to press a key on the keyboard
						screenRobot.keyPress(commandScanner.nextInt());
						break;
					case -4: // Command to release a key on the keyboard
						screenRobot.keyRelease(commandScanner.nextInt());
						break;
					case -5: // Command to move the mouse cursor
						screenRobot.mouseMove(commandScanner.nextInt(), commandScanner.nextInt());
						break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
