import java.net.*;

public class NATDetectionServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket1 = new DatagramSocket(6060);

        System.out.println("Waiting for first connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket);

        InetAddress IPAddress1 = receivePacket.getAddress();
        int port1 = receivePacket.getPort();

        System.out.println("Endpoint for first connection: " + IPAddress1 + ":" + port1);

        serverSocket1.close();

        serverSocket1 = new DatagramSocket(6061);

        System.out.println("Waiting for first connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket2 = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket2);

        InetAddress IPAddress2 = receivePacket2.getAddress();
        int port2 = receivePacket2.getPort();

        System.out.println("Endpoint for second connection: " + IPAddress2 + ":" + port2);

        serverSocket1.close();
    }
}