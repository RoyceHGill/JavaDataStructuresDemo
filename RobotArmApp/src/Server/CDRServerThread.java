package Server;

import Server.CDRServer;

import java.io.*;
import java.net.Socket;

public class CDRServerThread extends Thread
{
    private CDRServer server = null;
    private Socket socket = null;
    private  int ID = -1;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

    public CDRServerThread(CDRServer _server, Socket _socket)
    {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
    }

    public int getID()
    { return ID ;}


    public void send(String msg)
    {
        try
        {
            streamOut.writeUTF(msg);
            streamOut.flush();
        }
        catch (IOException e)
        {
            System.out.println(ID + " Error sending: " + e.getMessage());
            server.remove(ID);
            interrupt();
        }

    }

    public void run()
    {
        System.out.println("Server Thread " + ID + " running. ");
        while (true)
        {
            try
            {
                server.handle(ID, streamIn.readUTF());
            }
            catch (IOException e)
            {
                System.out.println(ID + " Error reading: " + e.getMessage());
                server.remove(ID);
                interrupt();
                return;
            }
        }
    }

    public void open() throws IOException
    {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException
    {
        if (socket != null)
        {
            socket.close();
        }
        if (streamIn != null)
        {
            streamIn.close();
        }
        if (streamOut != null)
        {
            streamOut.close();
        }
    }



}
