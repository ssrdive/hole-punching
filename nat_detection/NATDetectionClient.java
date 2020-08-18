import java.net.*;

public class NATDetectionClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();

        byte[] sendData = "Hello".getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);

        int localPort = clientSocket.getLocalPort();

        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6061);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);

        clientSocket.close();

        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6062);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);

        clientSocket.close();
    }
}