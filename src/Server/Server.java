package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    int port = 18525;
    ServerSocket serverSocket;

    private final int totalRooms = 10;//total number of rooms in hotel. It can be get from user at runtime but i preferred it that way
    String[] RoomCurrentStatus; //String array for storing state of every room, if someone check in then his room status will be updated to "occupied". if checked out then it will be updated to "vacant"
    private ArrayList bookingList; // //Arraylist for storing all instances of Tickets, or simply all occupied rooms
    public int roomIndex;

    public Server() throws IOException, ClassNotFoundException {
        bookingList = new ArrayList<>();
        RoomCurrentStatus = new String[totalRooms]; //Status of every room. It may be "vacant" or "occupied" but not both at the same time
        for (int i = 0; i <= RoomCurrentStatus.length - 1; i++) {
            RoomCurrentStatus[i] = "vacant"; //initially all rooms are initialized "vacnat"
        }
        createSocket(); //method to create socket
        connectReadWrite(); //method to establish connection and do Read write oerations
    }
    
    public void createSocket() throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Server Started...\nWaiting for Client on port " + serverSocket.getLocalPort() + "...");
    }
    
    public void connectReadWrite() throws IOException, ClassNotFoundException{
        while (true) {
            Socket newClient = serverSocket.accept();
            System.out.println("Connected to " + newClient.getRemoteSocketAddress());

            ObjectInputStream objectInput = new ObjectInputStream(newClient.getInputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(newClient.getOutputStream());
            objectOutput.writeObject(RoomCurrentStatus);//String Array RoomCurrentStatus is written to client
            Object object = objectInput.readObject();//Updated Array RoomCurrentStatus written by client is read here.
            String updatedRoomsStatus[] = (String[]) object;

            for (int i = 0; i <= updatedRoomsStatus.length - 1; i++) {
                if (updatedRoomsStatus[i].equalsIgnoreCase("occupied")) {
                    bookingList.add(i, object);//client object passed by Client.java is added to booked list.
                    System.out.println("Congratulations you have been assigned a room");
                    RoomCurrentStatus = updatedRoomsStatus;
                    return;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
