import java.net.*;

public class NATDetectionClient {
    public static void main(String[] args) {
        DatagramSocket clientSocket = new DatagramSocket();

        byte[] sendData = "Hello".getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        int localPort = clientSocket.getLocalPort();
        clientSocket.close();
        clientSocket = new DatagramSocket(localPort);

        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6061);
        clientSocket.send(sendPacket);

        clientSocket.close();
    }
}