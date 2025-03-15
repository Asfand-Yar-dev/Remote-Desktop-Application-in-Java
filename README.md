# 🖥️ Remote Desktop Application in Java

A **Remote Desktop Application** built in Java that enables users to securely control a computer over a network. This project includes features like **real-time screen sharing, remote mouse and keyboard control, and secure file transfer**.

## ✨ Features

- 🔍 **Remote Screen Sharing** – View and interact with a remote system in real-time.
- 🎮 **Mouse & Keyboard Control** – Fully operate the remote computer.
- 📁 **Secure File Transfer** – Send and receive files safely.
- ⚡ **Optimized Performance** – Low-latency interaction for a smooth experience.
- 🔐 **Encrypted Communication** – Ensures security and privacy.

## 🛠️ Technologies Used

- **Java (Swing & AWT)** – GUI & user interaction
- **Java Sockets** – Network communication
- **Multi-threading** – Efficient handling of multiple connections
- **Encryption** – Secure data transmission

## 🚀 Installation & Setup

### Prerequisites
- **Java JDK 8+** installed
- Basic knowledge of running Java applications

### Steps
1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/Remote-Desktop-Java.git
   cd Remote-Desktop-Java
   ```
2. **Compile the Java files**
   ```sh
   javac Server.java Client.java
   ```
3. **Run the server** (on the host machine):
   ```sh
   java Server
   ```
4. **Run the client** (on the remote machine):
   ```sh
   java Client <server-ip-address>
   ```
5. Start remote control and file transfer!

## 📌 Usage
- **Server:** Runs on the host computer and waits for client connections.
- **Client:** Connects to the server using its IP address and starts remote control.
- **File Transfer:** Securely send and receive files between connected machines.

## 📜 License
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

## 🤝 Contributing
Pull requests are welcome! Feel free to **fork** the repository and submit improvements.

## 📧 Contact
For issues or suggestions, open an [issue](https://github.com/your-username/Remote-Desktop-Java/issues) or reach out via email.

---

⭐ **If you like this project, consider giving it a star!** ⭐
