import java.io.*; // Import for input and output operations, including file handling
import java.net.*; // Import for network communication via sockets

/**
 * Server class to receive files from clients.
 */
public class Server {
    /**
     * Constructor to start the file server on a specified port.
     * @param p The port number on which the server listens for incoming connections.
     */
    public Server(String p) {
        int port = Integer.parseInt(p); // Convert port string to integer
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Continuously accept incoming connections from clients
            while (true) {
                Socket socket = serverSocket.accept(); // Accept a new client connection
                System.out.println("New peer connected");

                // Handle client in a new thread
                new PeerHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Print stack trace if an IO exception occurs
        }
    }
}

/**
 * Thread class to handle each client connection for file receiving.
 */
class PeerHandler extends Thread {
    private Socket socket; // Socket for communication with the client

    /**
     * Constructor to initialize the PeerHandler with a client socket.
     * @param socket The socket representing the client connection.
     */
    public PeerHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Method executed when the thread starts, handles file receiving from the client.
     */
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            String command = dis.readUTF(); // Read command from client

            // Check if client wants to send a file
            if (command.equals("SEND")) {
                receiveFile(dis); // Receive the file from client
            } else {
                System.out.println("No file received: Unexpected command.");
            }

        } catch (IOException ex) {
            ex.printStackTrace(); // Print stack trace if an IO exception occurs
        }
    }

    /**
     * Method to receive a file from the client and save it to the server.
     * @param dis DataInputStream connected to the client socket for reading file data.
     * @throws IOException If an IO error occurs during file receiving.
     */
    private void receiveFile(DataInputStream dis) throws IOException {
        String fileName = dis.readUTF(); // Read file name from client
        long fileSize = dis.readLong(); // Read file size from client

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[4096]; // Buffer to hold chunks of the file
            int read;
            long remaining = fileSize;

            // Read file data from client and write to FileOutputStream
            while ((read = dis.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                fos.write(buffer, 0, read); // Write buffer to file
                remaining -= read; // Update remaining bytes to read
            }
        }
        System.out.println("File " + fileName + " received from peer.");
    }
}
