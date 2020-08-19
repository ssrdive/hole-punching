import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5050);

        while(true) {
            Socket socket = server.accept();
            InetAddress addr = socket.getInetAddress();
            int port = socket.getPort();

            System.out.println("Client IP: " + addr.toString());
            System.out.println("Client Port: " + port);
        }
    }
}