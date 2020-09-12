import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Server {
    public static void main(String[] args) {
        PingServer pingServer = new PingServer();
        pingServer.start();

        CommServer commServer = new CommServer();
        commServer.start();
    }
}

class PingServer extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(5056);
            while (true) {
                Socket s = null;
                s = ss.accept();

                System.out.println("[PingServer] Peer connected: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("[PingServer] Assigning new thread to peer...");
                Thread t = new PingPeerHandler(s, dis, dos);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class CommServer extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(5058);
            while (true) {
                Socket s = null;

                s = ss.accept();

                System.out.println("[CommServer] Peer connected: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("[CommServer] Assigning new thread to peer...");
                Thread t = new CommPeerHandler(s, dis, dos);
                t.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class PingValue {
    final String timeStamp;
    final String publicIP;
    final int publicPort;
    final String privateIP;

    public PingValue(String timeStamp, String publicIP, int publicPort, String privateIP) {
        this.timeStamp = timeStamp;
        this.publicIP = publicIP;
        this.publicPort = publicPort;
        this.privateIP = privateIP;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public String getPublicIP() {
        return this.publicIP;
    }

    public int getPublicPort() {
        return this.publicPort;
    }

    public String getPrivateIP() {
        return this.privateIP;
    }

    @Override
    public String toString() {
        return timeStamp + " " + publicIP + ":" + publicPort
                + " " + privateIP;
    }
}

class DataStore {
    public static HashMap<String, PingValue> pingDetails = new HashMap<String, PingValue>();
}

class CommPeerHandler extends Thread {
    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;

    public CommPeerHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        DataStore dataStore = new DataStore();
        while (true) {
            try {
                String received = dis.readUTF();
                String receivedArr[] = received.split("~");
                String sendPacket;
                switch (receivedArr[0]) {
                    case "IPREQUEST":
                        sendPacket = "SUCCESS~" +
                                dataStore.pingDetails.get(receivedArr[1]).getPrivateIP();
                        dos.writeUTF(sendPacket);
                        break;
                    default:
                        sendPacket = "ACK";
                        dos.writeUTF(sendPacket);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class PingPeerHandler extends Thread {
    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;

    public PingPeerHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        DataStore dataStore = new DataStore();
        while (true) {
            try {
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);

                String received = dis.readUTF();
                String receivedArr[] = received.split("~");

                switch (receivedArr[0]) {
                    case "PING":
                        dataStore.pingDetails.put(receivedArr[1], new PingValue(
                                formattedDate, s.getInetAddress().getHostName(),
                                s.getPort(), receivedArr[2]
                        ));
                        break;
                }

                System.out.print("[PingPeerHandler] " + formattedDate + " "
                        + s.getInetAddress().getHostName()
                        + ":" + s.getPort() + " ");
                System.out.println(received);
                System.out.println(dataStore.pingDetails);
            } catch (IOException e) {
                try {
                    s.close();
                    System.out.println("[PingPeerHandler] Closing: " + s);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}