package client;

import java.util.Scanner;

public class UI {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("*************************************\n"
                + "*  Welcome to Hotel Booking System  *\n"
                + "*************************************");

        String name = "";
        int age = 0;

        System.out.print("Enter person name : ");
        name = s.nextLine();

        boolean validAge;
        do {
            try {
                System.out.print("Enter " + name + "'s age : ");
                age = Integer.parseInt(s.nextLine());
                validAge = true;
            } catch (NumberFormatException e) {
                System.out.println("Plz enter valid age");
                validAge = false;
            }
        } while (!validAge);
        new Client(name, age); //create new Client
    }
}
