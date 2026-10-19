package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 *  @author Student
 */
public class MessageTest {

    private Message message1 = new Message("0012345678", 0, "+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
    private Message message2 = new Message("0087654321", 1, "08575975889",
            "Hi Keegan, did you receive the payment?");

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", message1.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }
        Message longMessage = new Message("0012345678", 0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                longMessage.checkMessageLength());
    }

    @Test
    public void testRecipientCorrectlyFormatted() {
        assertEquals("Cell phone number successfully captured.", message1.checkRecipientCell());
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                message2.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        assertEquals("00:0:HITONIGHT", message1.createMessageHash());
    }

    @Test
    public void testMessageHashLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hi thanks"
        };
        String[] expectedHashes = {
            "00:0:HITONIGHT",
            "00:1:HIPAYMENT",
            "00:2:HITHANKS"
        };

        for (int i = 0; i < messages.length; i++) {
            Message m = new Message("0012345678", i, "+27718693002", messages[i]);
            assertEquals(expectedHashes[i], m.createMessageHash());
        }
    }

    @Test
    public void testMessageIDCreated() {
        String id = Message.generateMessageID();
        Message m = new Message(id, 0, "+27718693002", "Hello there");
        assertEquals("Message ID generated: " + id, m.displayMessageID());
        assertEquals(10, id.length());
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", message1.sentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", message2.sentMessage(2));
    }

    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", message1.sentMessage(3));
    }


    @Test
    public void testTotalMessagesSent() {
        int before = Message.returnTotalMessages();
        message1.sentMessage(1);   // sent, so the total goes up by 1
        message2.sentMessage(2);   // disregarded, so it does not count
        assertEquals(before + 1, Message.returnTotalMessages());
    }
}