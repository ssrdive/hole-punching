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
        clientSocket.close();

        clientSocket = new DatagramSocket(localPort);
        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);
        clientSocket.close();

        clientSocket = new DatagramSocket(localPort);
        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);
        clientSocket.close();

        clientSocket = new DatagramSocket(localPort);
        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);
        clientSocket.close();

        clientSocket = new DatagramSocket(localPort);
        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);
        clientSocket.close();

        clientSocket = new DatagramSocket(localPort);
        sendPacket = new DatagramPacket(sendData,
            sendData.length, InetAddress.getByName("178.128.214.46"), 6060);
        clientSocket.send(sendPacket);

        receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);
        clientSocket.close();
    }
}