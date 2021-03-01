# Server Client Handler
Built in Java, this program runs a server with multiple clients and allows for communication between all entities.

Please following the instructions below to carry out the following tasks.

1. Communication between server and client:
        a. Run ChatServer.java.
        b. Run ChatClient.java.
        c. Type a message.

2.  Allowing multiple clients to run:
        a. Run ChatServer.java.
        b. Run ChatClient.java.
        c. Run ChatClient.java again.

    Syntax for clients to speak to each other:

        Client (number)> (Message)

        Example:
        Client 1> Hi! How are you doing?

    To logoff:
        Type 'logoff' then disconnect.

3. Communication between client and chat bot:
        a. Run ChatServer.java.
        b. Run ChatClient.java.
        c. Run ChatBot.java.
        d. Type a message.
        e. Receive message from chat bot.

4. Client connecting to different ip addresses or ports (Input in linux terminal):

        a. different port:
        java ChatClient −ccp 14003

        b. different ip:
        java ChatClient −cca 192.168.10.250

        c. different port and ip:
        java ChatClient −cca 192.168.10.250 -ccp 14003

5. Server connecting to different port (Input in linux terminal):

        java ChatServer −csp 14003
