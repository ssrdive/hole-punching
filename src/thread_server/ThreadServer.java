import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class ThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5056);

        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("A new client connected: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for the client");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {
    DateFormat forDate = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat forTime = new SimpleDateFormat("hh:mm:ss");
    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toReturn;
        while (true) {
            try {
                dos.writeUTF("What do you want? [Date | Time]\n" +
                        "Type Exit to terminate connection. -> ");
                received = dis.readUTF();

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit.");
                    System.out.println("Closing connection");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                Date date = new Date();

                switch (received) {
                    case "Date":
                        toReturn = forDate.format(date);
                        dos.writeUTF(toReturn);
                        break;
                    case "Time":
                        toReturn = forTime.format(date);
                        dos.writeUTF(toReturn);
                        break;
                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}