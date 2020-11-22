import com.example.finalapproject.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Handler extends Thread {
    private Socket socket;
    String username = "";
    ObjectInputStream ois;
    ObjectOutputStream oos;
    DataInputStream dis;
    DataOutputStream dos;

    public Handler(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public int findIndex(String username, String password) {
        for (int i = 0; i < Server.database.size(); i++) {
            if (Server.database.get(i).getUsername().equals(username))
                if (Server.database.get(i).getPassword().equals(password)) {
                    return i;
                }
        }
        return -1;
    }

    int findUser(String username) {
        for (int i = 0; i < Server.database.size(); i++) {
            if (Server.database.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    int findHandler(String username) {
        for (int i = 0; i < Server.Handlers.size(); i++) {
            if (Server.Handlers.get(i).username.equals(username)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {
        try {
            String type = dis.readUTF();//what client want
            switch (type) {
                case "LogIn":


                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    System.out.println(username + " " + password);
                    int index = findIndex(username, password);
                    System.out.println(index);
                    if (index != -1) {
                        Server.database.get(index).isLoggedIn = true;
                        oos.writeObject(Server.database.get(index));
                    } else {
                        oos.writeObject(null);
                    }
                    break;


                case "SignUp":


                    User newUser = (User) ois.readObject();
                    String done = "done";
                    for (int i = 0; i < Server.database.size(); i++) {
                        if (Server.database.get(i).getUsername().equals(newUser.getUsername())) {
                            done = "error";
                            break;
                        }
                    }
                    if (done.equals("done")) {
                        Server.database.add(newUser);
                        Server.writeArray();
                    }
                    dos.writeUTF(done);

                    break;

                case "LeaderBoard":
                    Object o =ois.readObject();
                    oos.writeObject(Server.database);
                    break;
                case "newMessage":

                    Mess mess = (Mess) ois.readObject();
                    int index1 = findUser(mess.getReceiver().getUsername());
                    int index2 = findUser(mess.getSender().getUsername());
                    Server.database.get(index1).getMessages().add(mess);
                    Server.database.get(index2).getMessages().add(mess);
                    Server.writeArray();
                    break;


                case "XOWaiter":
                    Server.Handlers.clear();
                    XOPlayer xoPlayer = (XOPlayer) ois.readObject();
                    boolean flag = false;
                    Server.xoPlayers.add((xoPlayer));
                    if (Server.xoPlayers.size() == 2) {
                        Server.xoPlayers.get(0).isStarter = true;
                        oos.writeObject(Server.xoPlayers.get(0));
                    } else if (Server.xoPlayers.size() == 1) {
                        while (Server.xoPlayers.size() != 2) {
                            flag = !flag;
                        }
                        oos.writeObject(Server.xoPlayers.get(1));
                    }
                    Server.xoPlayers.clear();
                    break;
                case "OthelloWaiter":
                    Server.Handlers.clear();
                    OthelloPlayer othelloPlayer = (OthelloPlayer) ois.readObject();
                    flag = false;
                    Server.othelloPlayers.add((othelloPlayer));
                    if (Server.othelloPlayers.size() == 2) {
                        Server.othelloPlayers.get(0).isStarter = true;
                        oos.writeObject(Server.othelloPlayers.get(0));
                    } else if (Server.othelloPlayers.size() == 1) {
                        while (Server.othelloPlayers.size() != 2) {
                            flag = !flag;
                        }
                        oos.writeObject(Server.othelloPlayers.get(1));
                    }
                    Server.othelloPlayers.clear();
                    break;
                case "HangmanWaiter":
                    Server.Handlers.clear();
                    HangmanPlayer hangmanPlayer = (HangmanPlayer) ois.readObject();
                    flag = false;
                    Server.hangmanPlayers.add((hangmanPlayer));
                    if (Server.hangmanPlayers.size() == 2) {
                        Server.hangmanPlayers.get(0).isStarter = true;
                        oos.writeObject(Server.hangmanPlayers.get(0));
                    } else if (Server.hangmanPlayers.size() == 1) {
                        while (Server.hangmanPlayers.size() != 2) {
                            flag = !flag;
                        }
                        oos.writeObject(Server.hangmanPlayers.get(1));
                    }
                    Server.hangmanPlayers.clear();
                    break;
                case "XOGamerSender":
                    String x = dis.readUTF();
                    String y = dis.readUTF();
                    System.out.println(x + " " + y);
                    String oppUsername = dis.readUTF();
                    index = findHandler(oppUsername);
                    System.out.println(index);
                    Server.Handlers.get(index).dos.writeUTF(x);
                    Server.Handlers.get(index).dos.writeUTF(y);
                    break;
                case "OthelloGameSender":
                    char[][] board = (char[][]) ois.readObject();
                    oppUsername = dis.readUTF();
                    index = findHandler(oppUsername);
                    System.out.println(index);
                    Server.Handlers.get(index).oos.writeObject(board);
                    break;
                case "HangmanGameSender":
                    String s = dis.readUTF();
                    System.out.println(s);
                    oppUsername = dis.readUTF();
                    index = findHandler(oppUsername);
                    System.out.println(index);
                    Server.Handlers.get(index).dos.writeUTF(s);
                    break;
                case "XOGamerReceiver":
                    User i = (User) ois.readObject();
                    this.username = i.getUsername();
                    Server.Handlers.add(this);
                    System.out.println(this.username);
                    break;
                case "OthelloGamerReceiver":
                    i = (User) ois.readObject();
                    this.username = i.getUsername();
                    Server.Handlers.add(this);
                    System.out.println(this.username);
                    break;
                case "HangmanGamerReceiver":
                    String user = dis.readUTF();
                    this.username = user;
                    Server.Handlers.add(this);
                    System.out.println(this.username);
                    break;
                case "HangmanQuestioner":

                    Object o1 =ois.readObject();
                    String s1 = dis.readUTF();
                    index = findHandler(s1);
                    System.out.println(index);
                    Server.Handlers.get(index).oos.writeObject("done");
                    break;
                case "scoreChanged":
                    User tmp = (User) ois.readObject();
                    Server.database.get(findUser(tmp.getUsername())).setScores(tmp.getScores());
                    Server.writeArray();
                    break;
                case "finish":
                    Server.Handlers.clear();
                    break;

                case "AddFriend":

                    UserFriend userFriend = (UserFriend) ois.readObject();
                    index1 = findUser(userFriend.getTheUser());
                    index2 = findUser(userFriend.getFriendUsername());
                    if (index1 == index2) {
                        oos.writeObject("You can not be your own friend !");
                    } else if (index2 != -1) {
                        if (Server.database.get(index1).friends.contains(Server.database.get(index2))) {
                            oos.writeObject("This user is already your friend !");
                        } else {
                            Server.database.get(index1).friends.add(Server.database.get(index2));
                            Server.database.get(index2).friends.add(Server.database.get(index1));
                            oos.writeObject(Server.database.get(index1));
                            Server.writeArray();
                        }
                    } else {
                        oos.writeObject("This username is not founded !");
                    }

                    break;
               /* case "UpdateUser":
                    this.username = dis.readUTF();
                    Server.Handlers.add(this);
                    int myIndex = findUser(this.username);
                    while (true) {
                        oos.writeObject(Server.database.get(myIndex));
                        if (myIndex == -1)
                            break;
                    }
                    break;*/
                case "ChangeUsername":
                    String newName = dis.readUTF();
                    String LastUserName = dis.readUTF();
                    index1 = findUser(LastUserName);
                    index2 = findUser(newName);
                    if (index2 == -1) {
                        Server.database.get(index1).setUsername(newName);
                        oos.writeObject(Server.database.get(index1));
                        Server.writeArray();
                    } else {
                        oos.writeObject("This username is taken before ! , choose another one ");
                    }
                    break;


            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

