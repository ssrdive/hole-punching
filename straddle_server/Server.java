import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5056);

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
        while(true) {
            String received = dis.readUTF();
            System.out.println(received);
        }
    }
}