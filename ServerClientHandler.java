package com.company;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

// ServerClientHandler class
class ServerClientHandler implements Runnable
{
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    // Constructor
    public ServerClientHandler(Socket s, String name,
                               DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public void run() {
        String received;

        while (true)
        {
            try
            {

                // Receiving client input
                received = dis.readUTF();

                System.out.println(name + ": " + received);

                // The client is removed from the client list if they input 'logoff'
                if(received.equals("logoff")){
                    this.isloggedin=false;
                    this.s.close();
                    ChatServer.ClientList.remove(this);
                    LoggedOffMsg();
                    break;
                }

                // Identify private client messages
                if(received.contains(">")){
                    PrivateMessaging(received);
                }

                // Clients messages to server can be seen by all.
                // Therefore, if the message is not specifically directed to a user, then send to all.
                if(!received.contains(">")){
                    PublicMessaging(received);
                }
            } catch (IOException e) {
                System.exit(-1);
                e.printStackTrace();
            }

        }
        try
        {
            // Close input and output streams
            this.dis.close();
            this.dos.close();

        } catch(IOException e){
            System.exit(-1);
            e.printStackTrace();
        }
    }

    public void LoggedOffMsg() throws IOException {
        // For every client in the client list, send the message.
        for (ServerClientHandler client : ChatServer.ClientList)
        {
            // Once found, send the message that the client has logged off
            client.dos.writeUTF(this.name+" has logged off");
        }
    }

    // Enables clients to private message each other if they input the client name followed by >
    public void PrivateMessaging(String received) throws IOException {
        StringTokenizer st = new StringTokenizer(received, ">");
        String recipient = st.nextToken();
        String msg = st.nextToken();

        // Search for client who will receive message in previous list
        for (ServerClientHandler client : ChatServer.ClientList)
        {
            // Once found, send the message
            if (client.name.equals(recipient) && client.isloggedin==true)
            {
                client.dos.writeUTF(this.name+" : "+msg);
                break;
            }
        }
    }

    // If there is no private messaging, then all users can see the messages sent to the server
    public void PublicMessaging(String received) throws IOException {
        String CompleteMsg = " Message to server: " + received;

        // For every client in the client list, send the message.
        for (ServerClientHandler client : ChatServer.ClientList)
        {
            // Once found, send the message
            client.dos.writeUTF(this.name+CompleteMsg);
        }
    }


}
