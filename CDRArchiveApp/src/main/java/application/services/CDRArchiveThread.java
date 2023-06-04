package application.services;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class CDRArchiveThread extends Thread {

    private final Socket socket;
    private final CDRArchiveClient client;
    private DataInputStream streamIn;

    public CDRArchiveThread(CDRArchiveClient _client,
                            Socket _socket) {
        client = _client;
        socket = _socket;
        open();
        start();
    }

    public void open() {
        try {
            streamIn = new DataInputStream(socket
                    .getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream: " +
                    e);
            client.close();
        }
    }

    public void close() {
        try {
            if (null != streamIn) {
                streamIn.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing input stream: " +
                    e);
        }
    }

    public void run() {
        while (true)
            try {
                client.handle(streamIn.readUTF());
            } catch (IOException e) {
                System.out.println("Listening error: " +
                        e.getMessage());
                client.close();
                return;
            }
    }


}
