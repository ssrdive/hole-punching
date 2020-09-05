import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket();
        s.setReuseAddress(true);
        s.bind(new InetSocketAddress("172.20.10.2", 9000));
        s.connect(new InetSocketAddress("178.128.214.46", 5050));
        s.close();

//        s = new Socket();
//        s.bind(new InetSocketAddress("172.20.10.2", 9000));
//        s.connect(new InetSocketAddress("178.128.214.46", 5050));
//        s.close();
    }
}