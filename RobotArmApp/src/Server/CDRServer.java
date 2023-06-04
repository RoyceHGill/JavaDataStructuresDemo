package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CDRServer implements Runnable{
    private final CDRServerThread[] clients = new CDRServerThread[5];
    private ServerSocket server = null;
    private Thread thread = null;
    private int clientCount = 0;

public CDRServer(int port)
    {
        try
        {
            System.out.println("Binding to port " + port + "please wait...");
            server = new ServerSocket(port);
            System.out.println("Server stated: " + server);
            start();
        }
        catch (Exception e)
        {
            System.out.println("Can not bind to port " + port + ": " + e.getMessage());
        }
    }

    public void run()
    {
        while (thread != null)
        {
            try
            {
                System.out.println("Waiting for a client ...");
                addThread(server.accept());
            }
            catch (IOException e)
            {
                System.out.println("Server accept error: " + e);
                stop();
            }
        }
    }

    public  void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop()
    {
        if (thread != null)
        {
            thread.interrupt();
            thread = null;
        }
    }

    private int findClient(int ID)
    {
        for (int i = 0; i < clientCount; i++)
        {
            if (clients[i].getID() == ID)
            {
                return i;
            }
        }
        return -1;
    }

    public synchronized void handle(int ID, String input)
    {
        if (input.equals(".bye"))
        {
            remove(ID);
        }
        else if (input.equals("CLOSE"))
        {
            for (int i = 0; i < clientCount; i++) {
                clients[i].send("CLOSE");
            }
            System.exit(0);
        }
        else
        {
            for (int i = 0; i < clientCount; i++) {
                clients[i].send(ID + ";" + input);
            }
        }
    }

    public synchronized void remove(int ID)
    {
        int pos = findClient(ID);
        if (pos >= 0 )
        {
            CDRServerThread toTerminate = clients[pos];
            System.out.println("Removing client thread " + ID + " at " + pos);
            if (pos <clientCount - 1)
            {
                for (int i = 0; i < clientCount; i++)
                {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try
            {
                toTerminate.close();
            }
            catch (IOException e)
            {
                System.out.println("Error closing thread: " + e);
            }
            toTerminate.interrupt();
        }
    }

    private void addThread(Socket socket)
    {
        if (clientCount <= clients.length)
        {
            System.out.println("Client accepted: " + socket);
            clients[clientCount] = new CDRServerThread(this, socket);
            try
            {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            }
            catch (IOException e)
            {
                System.out.println("Error opening thread: " + e);
            }
        }
        else
        {
            System.out.println("Client refused: maximum " + clients.length + " reached.");
        }
    }

    public static void main(String[] args)
    {
        CDRServer server;
        if (args.length != 1)
        {
            //System.out.println("Usage: java ChatServer port");
            server = new CDRServer(4444);
        }
        else
        {
            server = new CDRServer(Integer.parseInt(args[0]));
        }
    }



}
