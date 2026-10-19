package com.mycompany.chatapp;

import java.util.Scanner;

/**
 * 
 *  @author Student
 */
public class Chatapp {

    public static void main(String[] args) {
 Scanner input = new Scanner(System.in);

        System.out.println("=== REGISTRATION ===");

        System.out.print("First name: ");
        String firstName = input.nextLine();

        System.out.print("Last name: ");
        String lastName = input.nextLine();

        System.out.print("Username (must contain _ and be 5 characters or less): ");
        String username = input.nextLine();

        System.out.print("Password (8+ characters, a capital, a number, a special character): ");
        String password = input.nextLine();

        System.out.print("Cell phone number (example +27838968976): ");
        String cellPhone = input.nextLine();

        Login user = new Login(firstName, lastName, username, password, cellPhone);

        String result = user.registerUser();
        System.out.println(result);

        if (result.startsWith("Welcome") == false) {
            return;
        }

        System.out.println("");
        System.out.println("=== LOGIN ===");

        System.out.print("Username: ");
        String loginUsername = input.nextLine();

        System.out.print("Password: ");
        String loginPassword = input.nextLine();

        boolean loggedIn = user.loginUser(loginUsername, loginPassword);
        System.out.println(user.returnLoginStatus(loggedIn));


        if (loggedIn == false) { // Only users who logged in can send messages
            return;
        }

        System.out.println("");
        System.out.println("Welcome to QuickChat.");

        System.out.print("How many messages do you want to enter? ");
        int numMessages = Integer.parseInt(input.nextLine());

        boolean messagesDone = false;
        String menuChoice = "";

    
 while (menuChoice.equals("3") == false) { // The menu keeps running until the user chooses 3 which
     //is quit

            System.out.println("");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            menuChoice = input.nextLine();

            if (menuChoice.equals("1")) {

                if (messagesDone == true) {
                    System.out.println("You have already entered all your messages.");
                } else {

                    for (int i = 0; i < numMessages; i++) {   // This loop runs once for 
                        //each message the user wants to enter

                        System.out.println("");
                        System.out.println("--- Message " + (i + 1) + " of " + numMessages + " ---");

                        String messageID = Message.generateMessageID();

                        System.out.print("Recipient cell number (example +27718693002): ");
                        String recipient = input.nextLine();

                        System.out.print("Message (max 250 characters): ");
                        String text = input.nextLine();

                        Message msg = new Message(messageID, i, recipient, text);

                       
                        if (msg.checkRecipientCell().startsWith("Cell phone number is")) {
                            System.out.println(msg.checkRecipientCell());
                            continue;
                        }
                        if (text.length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            continue;
                        }

                        System.out.println(msg.checkRecipientCell());
                        System.out.println(msg.displayMessageID());
                        System.out.println(msg.checkMessageLength());

                        // Ask what to do with the message
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choose an option: ");
                        String choiceText = input.nextLine();
                        //We ask the user what to do with the message

                        int choice = 0;
                        if (choiceText.matches("[1-3]")) {
                            choice = Integer.parseInt(choiceText);
                        }

                        System.out.println(msg.sentMessage(choice));

                        if (choice == 1) {
                            System.out.println(msg.messageDetails());
                        }

                        if (choice == 2) {
                            input.nextLine();
                            System.out.println("Message deleted.");
                        }
                    }

                    messagesDone = true;
                    System.out.println("");
                    System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
                }

            } else if (menuChoice.equals("2")) {
                System.out.println("Coming Soon.");

            } else if (menuChoice.equals("3")) {
                System.out.println("Goodbye!");

            } else {
                System.out.println("Invalid option, please enter 1, 2 or 3.");
            }
        }
    }
}