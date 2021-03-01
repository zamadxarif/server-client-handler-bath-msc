package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChatBot {

    // Initialise server socket
    private Socket server;

    public ChatBot(String ip, int ServerPort){
        try{
            server = new Socket(ip, ServerPort);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void go(){

        try{

            // Creating input and output streams
            DataInputStream dis = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());

            // Create a list of automated bot messages
            List<String> BotMessages = Arrays.asList(
                    "Hi! Hope you have a great day today.",
                    "You'll always pass failure on the way.",
                    "You're braver than you believe, and stronger than you seem, and smarter than you think.");

            // Thread to receive client messages and send out an automated message
            Thread readMessage = new Thread(new Runnable()
            {
                @Override
                public void run() {

                    while (true) {
                        try {
                            // Send to input stream. Allows to receive client messages
                            String msg = dis.readUTF();
                            System.out.println(msg);

                            // Identify a bot message from the list of messages.
                            int rnd = new Random().nextInt(BotMessages.size());
                            String OutputMsg = BotMessages.get(rnd);

                            // Send message to client
                            dos.writeUTF(OutputMsg);
                            // Make thread sleep for 10 seconds to avoid spamming client
                            Thread.sleep(10000);

                        } catch (IOException | InterruptedException e
                        ) {
                            System.exit(-1);
                            e.printStackTrace();
                        }
                    }

                }
            });

            // Start the thread to read and send automated messages
            readMessage.start();

        } catch(IOException e) {

            e.printStackTrace();

        }
    }

    public static void main(String[] args){
        ChatBot bot = new ChatBot("localhost",14001);
        bot.go();
    }

}
