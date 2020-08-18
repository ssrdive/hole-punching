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

        System.out.println("Endpoint of first connection: " + IPAddress1 + ":" + port1);

        serverSocket1.send(new DatagramPacket("Hello".getBytes(),
            "Hello".getBytes().length, IPAddress1, port1));

        serverSocket1.close();

        serverSocket1 = new DatagramSocket(6061);

        System.out.println("Waiting for second connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket2 = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket2);

        InetAddress IPAddress2 = receivePacket2.getAddress();
        int port2 = receivePacket2.getPort();

        System.out.println("Endpoint of second connection: " + IPAddress2 + ":" + port2);

        serverSocket1.send(new DatagramPacket("Hello".getBytes(),
            "Hello".getBytes().length, IPAddress2, port2));

        serverSocket1.close();

        serverSocket1 = new DatagramSocket(6062);

        System.out.println("Waiting for third connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket3 = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket3);

        InetAddress IPAddress3 = receivePacket3.getAddress();
        int port3 = receivePacket3.getPort();

        System.out.println("Endpoint of third connection: " + IPAddress3 + ":" + port3);

        serverSocket1.send(new DatagramPacket("Hello".getBytes(),
            "Hello".getBytes().length, IPAddress3, port3));

        serverSocket1.close();

        serverSocket1 = new DatagramSocket(6063);

        System.out.println("Waiting for fourth connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket4 = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket4);

        InetAddress IPAddress4 = receivePacket4.getAddress();
        int port4 = receivePacket4.getPort();

        System.out.println("Endpoint of fourth connection: " + IPAddress4 + ":" + port4);

        serverSocket1.send(new DatagramPacket("Hello".getBytes(),
            "Hello".getBytes().length, IPAddress4, port4));

        serverSocket1.close();

        serverSocket1 = new DatagramSocket(6064);

        System.out.println("Waiting for fifth connection "
            + serverSocket1.getLocalPort());
        DatagramPacket receivePacket5 = new DatagramPacket(new byte[1024], 1024);
        serverSocket1.receive(receivePacket5);

        InetAddress IPAddress5 = receivePacket5.getAddress();
        int port5 = receivePacket5.getPort();

        System.out.println("Endpoint of fifth connection: " + IPAddress5 + ":" + port5);

        serverSocket1.send(new DatagramPacket("Hello".getBytes(),
            "Hello".getBytes().length, IPAddress5, port5));

        serverSocket1.close();
    }
}