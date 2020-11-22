

import com.example.finalapproject.HangmanPlayer;
import com.example.finalapproject.OthelloPlayer;
import com.example.finalapproject.User;
import com.example.finalapproject.XOPlayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
    public static List<XOPlayer> xoPlayers = Collections.synchronizedList(new LinkedList<>());
    public static List<OthelloPlayer> othelloPlayers = Collections.synchronizedList(new LinkedList<>());
    public static List<HangmanPlayer> hangmanPlayers = Collections.synchronizedList(new LinkedList<>());
    public static List<Handler> Handlers = Collections.synchronizedList(new LinkedList<>());
    public static LinkedList<User> database = new LinkedList<>();
    public static void writeArray() {
        File f = new File("C:\\Users\\DNR\\Desktop\\file.srl");
        try {
            if (f.exists() && f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(database);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<User> read() throws IOException {

        // input = null;
        LinkedList<User> ReturnClass = null;
        File f = new File("C:\\Users\\DNR\\Desktop\\file.srl");
        if (!f.exists()) {
            f.createNewFile();
            return new LinkedList<User>();
        } else {
            try {
                FileInputStream readData = new FileInputStream(f);
                ObjectInputStream readStream = new ObjectInputStream(readData);
                ReturnClass = (LinkedList<User>) readStream.readObject();
                readStream.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return ReturnClass;
        }
    }




    public static void main(String[] args) throws IOException {
        database = read();
        for (int i = 0; i < database.size(); i++) {
            database.get(i).isLoggedIn = false;
        }
        ServerSocket ss = new ServerSocket(6000);
        while (true) {
            Socket socket = ss.accept();
            System.out.println("Client connected");
            Handler handler = new Handler(socket);
            handler.start();
        }

    }
}