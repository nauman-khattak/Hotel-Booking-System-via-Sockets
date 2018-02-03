package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {

    Scanner s = new Scanner(System.in);
    int port = 18525;

    Client client;
    String clientName;
    int clientAge;
    int roomNo;
    String date;
    Socket myClient;

    public Client(String name, int age) {
        this.clientName = name;
        this.clientAge = age;
        try {
            myClient = new Socket("localhost", port);
            System.out.println("Connected to " + myClient.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(myClient.getInputStream()));// Input from Server

            ObjectOutputStream objectOutput = new ObjectOutputStream(myClient.getOutputStream());// object output to the server
            ObjectInputStream objectInput = new ObjectInputStream(myClient.getInputStream());// object input from server
            Object object = objectInput.readObject();
            String roomsStatus[] = (String[]) object;
            for (int i = 0; i <= roomsStatus.length - 1; i++) {
                if (roomsStatus[i].equalsIgnoreCase("vacant")) {
                    roomsStatus[i] = "occupied";
                    this.roomNo = i;
                    this.date = new Date().toString();
                    objectOutput.writeObject(roomsStatus);
                    objectOutput.writeObject(client);
                    System.out.println(toString());
                    return;
                } else {
                    System.out.println("Sorry no vacant room.");
                    return;
                }
            }
            myClient.close();
        } catch (Exception e) {
            System.out.println("Unable to connect to server.\nError: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "{ " + "Name = " + clientName + ", Age = " + clientAge + ", Room No = " + roomNo + ", Date = " + date + '}';
    }
}
