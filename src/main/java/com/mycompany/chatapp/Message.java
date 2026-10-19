/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Student
 */

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;

    private static int totalMessages = 0;
    private static String sentMessages = "";
    private static String storedMessages = "";

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        //These are constructors which will save details of a message
    }

    public static String generateMessageID() {
        String id = "";
        for (int i = 0; i < 10; i++) {  // This makes a random ten-digit ID, adding one random digit each time
            int digit = (int) (Math.random() * 10);
            id = id + digit;
        }
        return id;
    }

    public String displayMessageID() {
        return "Message ID generated: " + messageID; // This shows that the ID that was created
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10; //this shows that the id must not be more than 
        //10 characters
    }

    public String checkRecipientCell() {
        if (recipient.matches("\\+27[0-9]{9}")) {  // The number must start with +27 followed by nine digits
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain "
                 + "an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) { // The message must not be longer than 250 characters
            return "Message ready to send.";
        } else {
            int tooMany = messageText.length() - 250;
            return "Message exceeds 250 characters by " + tooMany
                 + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);

        String[] words = messageText.split(" ");   // we cut the message into words
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;

        return hash.toUpperCase().replaceAll("[^A-Z0-9:]", ""); //Message nees to be all caps, and remove 
        //punctuation like ? and ,
    }


    public String messageDetails() {
        return "Message ID: " + messageID + "\n"
             + "Message Hash: " + createMessageHash() + "\n"
             + "Recipient: " + recipient + "\n"
             + "Message: " + messageText;
    }

   
    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessages = totalMessages + 1;
            sentMessages = sentMessages + messageDetails() + "\n\n";
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    public static String printMessages() { // Returns all the messages sent while the program is running
        return sentMessages;
    }

    public static int returnTotalMessages() {  // Returns how many messages were sent
        return totalMessages;
    }

    public void storeMessage() {
        String text = messageText.replace("\"", "\\\""); 

        String json = "  {\"messageID\": \"" + messageID + "\", "
                    + "\"messageHash\": \"" + createMessageHash() + "\", "
                    + "\"recipient\": \"" + recipient + "\", "
                    + "\"message\": \"" + text + "\"}";

        if (storedMessages.equals("")) {
            storedMessages = json;
        } else {
            storedMessages = storedMessages + ",\n" + json;
        }

        try {
            FileWriter writer = new FileWriter("messages.json");
            writer.write("[\n" + storedMessages + "\n]");
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save the file.");
        }
    }
}