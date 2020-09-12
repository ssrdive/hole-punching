import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5056);
        DataStore dataStore = new DataStore();

        while (true) {
            Socket s = null;
            try {
                s = ss.accept();

                System.out.println("Peer connected: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread to peer...");
                Thread t = new PeerHandler(s, dis, dos);
                t.start();
            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class PingStatusValue {
    public String timeStamp;
    public String publicIP;
    public int publicPort;
    public String privateIP;

    public PingStatusValue(String timeStamp, String publicIP, int publicPort, String privateIP) {
        this.timeStamp = timeStamp;
        this.publicIP = publicIP;
        this.publicPort = publicPort;
        this.privateIP = privateIP;
    }

    @Override
    public String toString() {
        return timeStamp + " " + publicIP + ":" + publicPort
                + " " + privateIP;
    }
}

class DataStore {
    public static HashMap<String, PingStatusValue> pingDetails = new HashMap<String, PingStatusValue>();
}

class PeerHandler extends Thread {
    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;
    public PeerHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        DataStore dataStore = new DataStore();
        while(true) {
            try {
                LocalDateTime myDateObj = LocalDateTime.now();  
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
                String formattedDate = myDateObj.format(myFormatObj); 

                String received = dis.readUTF();
                String receivedArr[] = received.split("~");

                switch (receivedArr[0]) {
                    case "PING":
                        dataStore.pingDetails.put(receivedArr[1], new PingStatusValue(
                                formattedDate, s.getInetAddress().getHostName(),
                                s.getPort(), receivedArr[2]
                        ));
                        break;
                }

                System.out.print(formattedDate + " " + s.getInetAddress().getHostName()
                + ":" + s.getPort() + " ");
                System.out.println(received);
                System.out.println(dataStore.pingDetails);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}