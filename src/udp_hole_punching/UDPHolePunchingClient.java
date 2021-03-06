import java.net.*;

public class UDPHolePunchingClient {

    public static void main(String[] args) throws Exception {
        // obtaining port number from arguments 
        int portNumber = Integer.parseInt(args[0]);
        
        // prepare Socket
        DatagramSocket clientSocket = new DatagramSocket();

        // prepare Data
        byte[] sendData = "Hello".getBytes();

        // send Data to ServerTCP with fix IP (X.X.X.X)
        // Client1 uses port 7070, Client2 uses port 7071
        DatagramPacket sendPacket = new DatagramPacket(sendData,
                sendData.length, InetAddress.getByName("206.189.39.61"), portNumber);
        clientSocket.send(sendPacket);

        // receive Data ==> Format:"<IP of other Client>-<Port of other Client>"
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        clientSocket.receive(receivePacket);

        // Convert Response to IP and Port
        String response = new String(receivePacket.getData());
        String[] splitResponse = response.split("-");
        InetAddress ip = InetAddress.getByName(splitResponse[0].substring(1));

        int port = Integer.parseInt(splitResponse[1]);

        // output converted Data for check
        System.out.println("IP: " + ip + " PORT: " + port);

        // close socket and open new socket with SAME localport
        int localPort = clientSocket.getLocalPort();
        clientSocket.close();
        clientSocket = new DatagramSocket(localPort);

        // set Timeout for receiving Data
        clientSocket.setSoTimeout(1000);

        // send 5000 Messages for testing
        for (int i = 0; i < 5000; i++) {

            // send Message to other client
            sendData = ("Datapacket(" + i + ")").getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            clientSocket.send(sendPacket);

            // receive Message from other client
            try {
                receivePacket.setData(new byte[1024]);
                clientSocket.receive(receivePacket);
                System.out.println("REC: "
                        + new String(receivePacket.getData()));

            } catch (Exception e) {
                System.out.println("SERVER TIMED OUT");
            }
        }

        // close connection
        clientSocket.close();
    }

}
