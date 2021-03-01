package com.company;

import java.io.*;
import java.util.*;
import java.net.*;

// ChatServer class
public class ChatServer
{

    // Storing clients on a list
    static Vector<ServerClientHandler> ClientList = new Vector<>();

    // Counter for each client
    static int i;

    // Initialise objects for the server socket and socket.
    private ServerSocket ss;
    private Socket s;

    // Creating synchronised exit variable
    private Boolean exit;

    // ChatServer constructor
    public ChatServer(int port) {
        try{

            ss = new ServerSocket(port);
            // counter for clients
            i = 0;
            exit = false;

            new Thread() {
                public void run(){
                    try{
                        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                        while(true) {
                            String in = userIn.readLine();
                            if(in.equals("EXIT")){
                                synchronized (exit){
                                    exit = true;
                                    // Shut down server if server user inputs 'EXIT'
                                    System.exit(-1);
                                }
                            }
                        }
                    } catch(IOException e){

                    }
                }
            }.start();
        } catch (IOException e){
            //e.printStackTrace();
            System.out.println(e.getMessage() + "Failed to create server socket ");
        }
    }

    public synchronized boolean getExit() {
        return exit;
    }

    public void run() throws IOException {

        // Continuously accepting clients to support handling multiple client requests
        // Loop runs if user of server does not input 'EXIT'
        while (!getExit()) {

            s = ss.accept();
            System.out.println("New client request received : " + s);

            try {

                // Creating input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // Create a new handler object for handling this request.
                ServerClientHandler clients = new ServerClientHandler(s, "client " + i, dis, dos);

                // Create a new Thread with this object.
                Thread t = new Thread(clients);

                System.out.println("Client added to active list");

                // add this client to active clients list
                ClientList.add(clients);

                LoginMsg("client " + i);

                // starting the thread
                t.start();

                // Add 1 to i to assign to another client
                i++;

            } catch (IOException e){

                ss.close();
                s=null;
                System.gc();
                s = ss.accept();
                e.printStackTrace();
                System.out.println("Not connected");

            }
        }
    }

    // Method to display when a client has logged in
    public void LoginMsg(String name) throws IOException {
        String CompleteMsg = " has logged in";

        // For every client in the client list, send the message.
        for (ServerClientHandler client : ChatServer.ClientList)
        {
            // Once found, send the message
            client.dos.writeUTF(name+CompleteMsg);
        }
    }

    public static void main(String[] args) throws IOException
    {
        // Server Port
        int port = 14001;

        // Change default port if client inputs '-csp'
        if(args.length!=0){
            try{
                if(args[0] == "-csp"){
                    port = Integer.parseInt(args[1]);
                }
            } catch(NumberFormatException e){
                port = 14001;
            }
        }

        ChatServer server = new ChatServer(port);
        server.run();

    }

}





/*
reference these for clients talking to each other:
https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/
https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
michael's notes
https://fullstackmastery.com/courses/MUC

for socket reconnection:
https://stackoverflow.com/questions/62929/java-net-socketexception-connection-reset

vector removing:
https://www.geeksforgeeks.org/vector-remove-method-in-java/

safely stopping java server:
https://stackoverflow.com/questions/37843506/how-can-i-safely-stop-this-java-server-program

 */

