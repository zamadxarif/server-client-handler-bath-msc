# Server Client Handler
Built in Java, this program runs a server with multiple clients and allows for communication between all entities.

Please following the instructions below to carry out the following tasks.

1. Communication between server and client:

        1. Run ChatServer.java.
        2. Run ChatClient.java.
        3. Type a message.

2.  Allowing multiple clients to run:

        1. Run ChatServer.java.
        2. Run ChatClient.java.
        3. Run ChatClient.java again.

    Syntax for clients to speak to each other:

        Client (number)> (Message)

        Example:
        Client 1> Hi! How are you doing?

    To logoff:
        Type 'logoff' then disconnect.

3. Communication between client and chat bot:

        1. Run ChatServer.java.
        2. Run ChatClient.java.
        3. Run ChatBot.java.
        4. Type a message.
        5. Receive message from chat bot.

4. Client connecting to different ip addresses or ports (Input in linux terminal):

        1. different port:
        java ChatClient −ccp 14003

        2. different ip:
        java ChatClient −cca 192.168.10.250

        3. different port and ip:
        java ChatClient −cca 192.168.10.250 -ccp 14003

5. Server connecting to different port (Input in linux terminal):

        java ChatServer −csp 14003
