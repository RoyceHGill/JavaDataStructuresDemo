package application.services;

import application.models.CDRModel;
import application.models.ProcessLogRecord;
import application.screens.MainScreen;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class CDRArchiveClient {
    private final DataInputStream console = null;
    private final String serverName = "localhost";
    private final int serverPort = 4444;
    private Socket socket;
    private DataOutputStream streamOutput;
    private CDRArchiveThread client;
    private final MainScreen _main;


    public CDRArchiveClient(MainScreen main) {
        _main = main;
        connect(serverName, serverPort);
    }

    private static void buildObjectMessage(CDRModel sentObject, StringBuilder message, String label) {
        message.append("M;");
        message.append(label);
        message.append(";");
        message.append(sentObject.getBarCode());
        message.append(";");
        message.append(sentObject.getId());
        message.append(";");
        message.append(sentObject.getTitle());
        message.append(";");
        message.append(sentObject.getYAxis());
        message.append(";");
        message.append(sentObject.getXAxis());
        message.append(";");
        message.append(sentObject.getAuthor());
        message.append(";");
        message.append(sentObject.getDescription());
        message.append(";");
        message.append(sentObject.getOnLoan());
        message.append(";");
        message.append(sentObject.getSection());
    }

    public void reconnect() {

        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe) {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe) {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void connect(String serverName, int serverPort) {
        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe) {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe) {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public String println(String msg) {
        //display.appendText(msg + "\n");
        return msg;
    }

    public void open() {
        try {
            streamOutput = new DataOutputStream(socket.getOutputStream());
            client = new CDRArchiveThread(this, socket);
        }
        catch (IOException e) {
            println("Error opening output stream: " + e);
        }
    }

    public void send(String message) {
        try {
            streamOutput.writeUTF(message);
            streamOutput.flush();
        }
        catch (IOException e) {
            println("Sending error: " + e.getMessage());
            close();
        }
    }

    public void send(CDRModel sentObject, String label) {
        StringBuilder message = new StringBuilder();
        switch (label) {
            case "Retrieve", "Return", "Remove", "Mostly Sorted", "Reverse Sorted", "Random Collection Sort" ->
                    CDRArchiveClient.buildObjectMessage(sentObject, message, label);
            case "Add" -> buildAddMessage(message, label);
        }

        try {
            streamOutput.writeUTF(message.toString());
            streamOutput.flush();
        }
        catch (IOException e) {
            println("Sending error: " + e.getMessage());
            close();
        }
    }

    private void buildAddMessage(StringBuilder message, String label) {
        message.append("M;");
        message.append(label);
    }

    public void close() {
        try {
            if (null != streamOutput) {
                streamOutput.close();
            }
            if (null != socket) {
                socket.close();
            }
        }
        catch (IOException ioe) {
            println("Error closing ...");
        }
        client.close();
        client.interrupt();
    }


    //for handling an incoming message.
    public void handle(String message) {
        if ("CLOSE".equals(message)) {
            System.out.println("Closing down app");
            for (int i = 3; 0 < i; i--) {
                try {
                    Thread.sleep(0);
                    System.out.println("Closing in " + i);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.exit(0);
        }
        String[] msgComponents = message.split(";");
        if ("S".equals(msgComponents[ 1 ])) {
            switch (msgComponents[ 2 ]) {
                case "Item Retrieved" -> {
                    _main.markCDRRetrieved(msgComponents);
                    JOptionPane.showMessageDialog(null, "CD is ready to be retrieved.");
                }
                case "New Item Added" -> {
                    _main.setupItemToAdd(msgComponents);
                    JOptionPane.showMessageDialog(null, "CD Details are now returned.");
                }
                case "Item Returned" -> {
                    _main.markCDRReturned(msgComponents);
                    JOptionPane.showMessageDialog(null, "CD is now returned");
                }
                case "Item Removed" -> {
                    _main.removeCDR(msgComponents);
                    JOptionPane.showMessageDialog(null, "CD removed from System.");
                }
                case "Item Sorted", "Item Reverse Sorted" -> {
                    CDRModel updatedCD = extractCDR(msgComponents);
                    _main.crudPanel.isNewEntry = false;
                    _main.selectedCDR = updatedCD;
                    _main.crudPanel.txtID.setText(Integer.toString(updatedCD.getId()));
                    _main.crudPanel.txtTitle.setText(updatedCD.getTitle());
                    _main.crudPanel.txtAuthor.setText(updatedCD.getAuthor());
                    _main.crudPanel.txtSection.setText(updatedCD.getSection());
                    _main.crudPanel.txtX.setText(Integer.toString(updatedCD.getXAxis()));
                    _main.crudPanel.txtY.setText(Integer.toString(updatedCD.getYAxis()));
                    _main.crudPanel.txtBarcode.setText(updatedCD.getBarCode());
                    _main.crudPanel.txtDescription.setText(updatedCD.getDescription());
                    _main.selectedCDR.setOnLoan(updatedCD.getOnLoan());
                    _main.saveCDR();
                }
            }
            ProcessLogRecord newRetrieveEvent = extractProcessLog(msgComponents);
            _main.updateProcessLog(newRetrieveEvent);
        }
        else {
        }
    }


    private ProcessLogRecord extractProcessLog(String[] msgComponents) {
        ProcessLogRecord newEvent = new ProcessLogRecord();
        try {
            newEvent.setDate(LocalDateTime.parse(msgComponents[ 12 ]));
            newEvent.setCommunicationType("RCVD");
            newEvent.setAction(msgComponents[ 2 ]);
            newEvent.setBarcode(msgComponents[ 3 ]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newEvent;
    }

    private CDRModel extractCDR(String[] msgComponents) {
        CDRModel sentCDR = new CDRModel();
        try {
            sentCDR.setBarCode(msgComponents[ 3 ]);
            sentCDR.setId(Integer.parseInt(msgComponents[ 4 ]));
            sentCDR.setTitle(msgComponents[ 5 ]);
            sentCDR.setYAxis(Integer.parseInt(msgComponents[ 6 ]));
            sentCDR.setXAxis(Integer.parseInt(msgComponents[ 7 ]));
            sentCDR.setAuthor(msgComponents[ 8 ]);
            sentCDR.setDescription(msgComponents[ 9 ]);
            sentCDR.setOnLoan(Boolean.parseBoolean(msgComponents[ 10 ]));
            sentCDR.setSection(msgComponents[ 11 ]);

        }
        catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return sentCDR;
    }
}
