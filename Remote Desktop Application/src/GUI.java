import Client.Start; // Importing the Start class from the Client package
import Server.SetPassword; // Importing the SetPassword class from the Server package

import javax.swing.*; // Importing Swing library for GUI components
import java.awt.*; // Importing AWT library for layout and color management
import java.awt.event.ActionEvent; // Importing AWT event handling for action events
import java.awt.event.ActionListener; // Importing AWT event handling for action listeners
import java.awt.event.MouseAdapter; // Importing AWT event handling for mouse events
import java.awt.event.MouseEvent; // Importing AWT event handling for mouse events
public class GUI extends JFrame {
    private String serverIpAddress = "localhost"; // Default server IP address
    private int serverPort = 1235; // Default server port

    // Constructor for the RemoteDesktopApp class
    public GUI() {
        setTitle("Remote Desktop Application"); // Set the title of the window

        // Create a panel for the buttons (bottom panel)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Set layout for the button panel
        buttonPanel.setBackground(new Color(0, 98, 128)); // Set background color for the button panel

        // Create buttons with styled appearance
        JButton setServerPasswordButton = createStyledButton("Set Password"); //Set password to the server
        JButton connectToDesktopButton = createStyledButton("Connect"); //Connect to the Server
        JButton sendFileButton = createStyledButton("Post File"); //Send File
        JButton receiveFileButton = createStyledButton("Get File"); //Receive File

        // Add action listener to "Server Password" button
        setServerPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    SetPassword passwordFrame = new SetPassword();
                    passwordFrame.setSize(300, 80);
                    passwordFrame.setLocationRelativeTo(null); // Center the frame on screen
                    passwordFrame.setVisible(true);
                });
            }
        });

        connectToDesktopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String ipAddress = JOptionPane.showInputDialog("Please enter server IP");
                    if (ipAddress != null && !ipAddress.isEmpty()) {
                        // Assuming you want to connect using the default port 4907 as defined in Start class
                        Start start = new Start();
                        start.initialize(ipAddress, 4907); // Change port as necessary

                        // Optionally, you could provide feedback that the connection attempt is being made
                        JOptionPane.showMessageDialog(null, "Attempting to connect to the server...");

                        // No need to create and show the SetPassword frame here
                    } else {
                        JOptionPane.showMessageDialog(null, "IP Address cannot be empty.");
                    }
                });
            }
        });



        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField ip = new JTextField(15);
                JLabel ipLabel = new JLabel("IP Address: ");
                JTextField port = new JTextField(5);
                JLabel portLabel = new JLabel("Port: ");
                JPanel ipAndPortPanel = new JPanel();
                ipAndPortPanel.setLayout(new GridLayout(2, 2, 10, 10));
                ipAndPortPanel.add(ipLabel);
                ipAndPortPanel.add(ip);
                ipAndPortPanel.add(portLabel);
                ipAndPortPanel.add(port);

                int choice = JOptionPane.showConfirmDialog(null, ipAndPortPanel, "Send Files", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    String ipAddress = ip.getText();
                    String portStr = port.getText();
                    if (!ipAddress.isEmpty() && !portStr.isEmpty()) {
                        Client fileClient = new Client(ipAddress, portStr);
                        fileClient.connectAndSendFile(); // Call the method to send the file
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter both IP Address and Port.");
                        actionPerformed(e); // Retry if the input is invalid
                    }
                }
            }
        });

        receiveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField port = new JTextField(5);
                JLabel portlabel = new JLabel("Port: ");
                JPanel ipandport = new JPanel();
                ipandport.setLayout(new GridLayout(2,1,10,10));
                ipandport.add(portlabel);
                ipandport.add(port);
                int choice = JOptionPane.showConfirmDialog(null, ipandport, "Receive Files", JOptionPane.OK_CANCEL_OPTION);
                String Port = port.getText();
                if(choice == JOptionPane.OK_OPTION && !Port.isEmpty())
                {
                    Server fs = new Server(Port);
                }
                else if(choice == JOptionPane.CLOSED_OPTION || choice == JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Enter Ip And Port");
                    actionPerformed(e);
                }
         }
});

        // Add buttons to the button panel
        buttonPanel.add(setServerPasswordButton);
        buttonPanel.add(connectToDesktopButton);
        buttonPanel.add(sendFileButton);
        buttonPanel.add(receiveFileButton);

        // Add the button panel to the bottom of the window
        add(buttonPanel, BorderLayout.SOUTH);

        // Create a right panel for the application title and file sharing history
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // Set layout for the right panel
        rightPanel.setBackground(new Color(0, 98, 128)); // Set background color for the right panel
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding inside the right panel

        // Add the application title with a colored background
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 98, 128)); // Set background color for the title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding inside the title panel
        JLabel titleLabel = new JLabel("Remote Desktop Application", SwingConstants.CENTER); // Create a label for the title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size for the title
        titleLabel.setForeground(Color.WHITE); // Set text color for the title
        titlePanel.add(titleLabel); // Add the title label to the title panel

        // Add the title panel to the north of the right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Create a list for file sharing history
        DefaultListModel<String> fileSharingHistoryListModel = new DefaultListModel<>(); // Create a model for the list
        JList<String> fileSharingHistoryList = new JList<>(fileSharingHistoryListModel); // Create a list for file sharing history
        fileSharingHistoryList.setBackground(Color.WHITE); // Set background color for the list
        fileSharingHistoryList.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Set border for the list

        // Add the history list to a scroll pane and add to the right panel
        JScrollPane historyScrollPane = new JScrollPane(fileSharingHistoryList); // Create a scroll pane for the list
        historyScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding inside the scroll pane
        rightPanel.add(historyScrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the right panel

        // Add the right panel to the center of the window
        add(rightPanel, BorderLayout.CENTER);

        // Set the default close operation and window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setSize(800, 400); // Set size of the window
        setLocationRelativeTo(null); // Center the window on the screen
    }

    // Method to create styled buttons
    private JButton createStyledButton(String buttonText) {
        JButton button = new JButton(buttonText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); // Create a Graphics2D object
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
                g2.setColor(getBackground()); // Set color to background color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Draw rounded rectangle
                g2.setColor(getForeground()); // Set color to foreground color
                g2.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the button text
                FontMetrics fm = g2.getFontMetrics(); // Get font metrics
                int stringWidth = fm.stringWidth(getText()); // Get text width
                int stringHeight = fm.getAscent(); // Get text height
                g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 4); // Draw the text
                g2.dispose(); // Dispose the Graphics2D object
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create(); // Create a Graphics2D object
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
                g2.setColor(new Color(0, 98, 128)); // Set color for the border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Draw rounded rectangle for the border
                g2.dispose(); // Dispose the Graphics2D object
            }
        };
        button.setForeground(Color.WHITE); // Set text color
        button.setBackground(new Color(0, 98, 128)); // Set background color for buttons
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Set border for buttons

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(244, 241, 241, 102)); // Change background color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 98, 128)); // Revert background color on exit
            }
        });

        return button; // Return the styled button
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI app = new GUI(); // Create a new instance of the application
            app.setVisible(true); // Make the application window visible
        });
    }
}
