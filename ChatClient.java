package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient
{
    private Socket server;

    // Constructor for chat client object.
    public ChatClient(String ip, int ServerPort){
        try{
            server = new Socket(ip, ServerPort);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not connect to server.");
            System.exit(-1);
        }
    }

    public void run(){

        try{
            Scanner scn = new Scanner(System.in);

            // Creating input and output streams
            DataInputStream dis = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());

            // Send the message to the server which then sends to other clients
            Thread sendMessage = new Thread( new Runnable()
            {
                @Override
                public void run() {
                    while (true) {

                        // Scan the message
                        String msg = scn.nextLine();

                        try {

                            // Send to output stream
                            dos.writeUTF(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // Reading message sent from the server.
            Thread readMessage = new Thread(new Runnable()
            {
                @Override
                public void run() {

                    while (true) {
                        try {

                            // Send to input stream
                            String msg = dis.readUTF();
                            System.out.println(msg);

                        } catch (IOException e) {
                            // If there is an error receiving from the server, the client is logged off.
                            // This will happen either if the server shuts down and if the client asks to log off.
                            System.out.println("Logoff successful");
                            System.exit(-1);
                            e.printStackTrace();
                        }
                    }
                }
            });

            // Starting the reading and sending threads.
            sendMessage.start();
            readMessage.start();

        } catch(IOException e) {
            System.exit(-1);
            e.printStackTrace();

        }
    }

    // Allow the client to change the port
    public static Integer CommandInputPort(String[] args, int port) {
        if (args.length != 0) {
            try {

                // If the user is only changing the port
                if (args.length <= 2){
                    if (args[0] == "-ccp") {
                        port = Integer.parseInt(args[1]);
                    }
                }

                // If the user is changing the IP and port at the same time
                if (args.length > 2) {

                    if (args[2] == "-ccp") {
                        port = Integer.parseInt(args[3]);
                    }

                    if (args[0] == "-ccp") {
                        port = Integer.parseInt(args[1]);
                    }
                }
            } catch (NumberFormatException e) {
                // default port is 14001
                port = 14001;
            }
        } return(port);
    }

    // Allow the client to change the IP address
    public static String CommandInputIP(String[] args, String ip) {
        if (args.length != 0) {
            try {
                if (args.length <= 2){
                    if (args[0] == "-cca") {
                        ip = args[1];
                    }
                }

                if (args.length > 2) {

                    if (args[2] == "-cca") {
                        ip = args[3];
                    }

                    if (args[0] == "-cca") {
                        ip = args[1];
                    }
                }
            } catch (NumberFormatException e) {
                ip = "localhost";
            }
        } return(ip);
    }

    public static void main(String[] args){

        int port = 14001;
        String ip = "localhost";

        port = CommandInputPort(args, port);
        ip = CommandInputIP(args, ip);

        ChatClient client = new ChatClient(ip,port);
        client.run();
    }

}