import javax.swing.*;// Import for GUI components like JFileChooser and JFrame
import java.io.*; // Import for input and output operations, including file handling
import java.net.Socket; // Import for network communication via sockets

/**
 * Client class to connect to a server and send a file.
 */
public class Client {
    private String serverAddress; // Server address to connect to
    private int serverPort; // Port number of the server

    /**
     * Constructor to initialize server address and port.
     * @param serverAddress The IP address or hostname of the server.
     * @param sp The port number of the server as a string.
     */
    public Client(String serverAddress, String sp) {
        this.serverAddress = serverAddress;
        this.serverPort = Integer.parseInt(sp); // Convert port string to integer
    }

    /**
     * Method to connect to the server and send a selected file.
     */
    public void connectAndSendFile() {
        // Create a file chooser dialog for selecting the file to send
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file to send");

        // Display the file chooser dialog and wait for user selection
        int userSelection = fileChooser.showOpenDialog(new JFrame());
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSend = fileChooser.getSelectedFile(); // Get the selected file
            try (Socket socket = new Socket(serverAddress, serverPort);
                 DataInputStream dis = new DataInputStream(socket.getInputStream());
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                System.out.println("Connected to the server.");

                // Send "SEND" command to server
                dos.writeUTF("SEND");

                // Send file name and size to server
                dos.writeUTF(fileToSend.getName()); // Send file name
                dos.writeLong(fileToSend.length()); // Send file size

                // Send file content to server in chunks
                try (FileInputStream fis = new FileInputStream(fileToSend)) {
                    byte[] buffer = new byte[4096]; // Buffer to hold chunks of the file
                    int read;
                    while ((read = fis.read(buffer)) > 0) { // Read file into buffer
                        dos.write(buffer, 0, read); // Write buffer to output stream
                    }
                }
                System.out.println("File " + fileToSend.getName() + " sent to server.");

            } catch (IOException ex) {
                ex.printStackTrace(); // Print stack trace if an IO exception occurs
            }
        }
    }
}
