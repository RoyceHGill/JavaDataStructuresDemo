package SecondaryApplication.Services;

import SecondaryApplication.Models.CDRModel;
import SecondaryApplication.Screens.Panels.SecondaryTablePanel;
import SecondaryApplication.Screens.SecondaryScreen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class CDRArmClient {
    private Socket socket = null;
    private final DataInputStream console = null;
    private DataOutputStream streamOutput = null;
    private CDRArmThread client = null;
    private final String serverName = "localhost";
    private final int serverPort = 4444;
    private SecondaryTablePanel _secondaryTablePanel = null;
    private SecondaryScreen _secondaryScreen = null;
    private CDRModel selectedCDR = new CDRModel();
    int barcodeLength = 8;


    public CDRArmClient(SecondaryTablePanel secondaryTablePanel, SecondaryScreen secondaryScreen) {
        _secondaryTablePanel = secondaryTablePanel;
        _secondaryScreen = secondaryScreen;

        connect(serverName, serverPort);
    }

    public void connect(String serverName, int serverPort) {
        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch ( UnknownHostException uhe ) {
            println("Host unknown: " + uhe.getMessage());
        }
        catch ( IOException ioe ) {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void println(String msg) {
        //display.appendText(msg + "\n");
        System.out.println(msg);
    }

    public void open() {
        try {
            streamOutput = new DataOutputStream(socket.getOutputStream());
            client = new CDRArmThread(this, socket);
        }
        catch ( IOException e ) {
            println("Error opening output stream: " + e);
        }
    }

    public void send(String message) {
        try {
            streamOutput.writeUTF(message);
            streamOutput.flush();
        }
        catch ( IOException e ) {
            println("Sending error: " + e.getMessage());
            close();
        }
    }

    public void send(CDRModel sentObject, String responseMessage) {
        String message = BuildMessageStringFromSecondary(sentObject, responseMessage);

        try {
            streamOutput.writeUTF(message);
            streamOutput.flush();
        }
        catch ( IOException e ) {
            println("Sending error: " + e.getMessage());
            close();
        }
    }

    private static String BuildMessageStringFromSecondary(CDRModel sentObject, String responseMessage) {
        String message = "S;" +
                responseMessage +
                ";" +
                sentObject.getBarCode() +
                ";" +
                sentObject.getId() +
                ";" +
                sentObject.getTitle() +
                ";" +
                sentObject.getYAxis() +
                ";" +
                sentObject.getXAxis() +
                ";" +
                sentObject.getAuthor() +
                ";" +
                sentObject.getDescription() +
                ";" +
                sentObject.getOnLoan() +
                ";" +
                sentObject.getSection() +
                ";" +
                LocalDateTime.now();

        return message;
    }

    public void close() {
        try {
            if (streamOutput != null) {
                streamOutput.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch ( IOException ioe ) {
            println("Error closing ...");
        }
        client.close();
        client.interrupt();
    }


    //for handling an incoming message.
    public void handle(String msg) {
        _secondaryTablePanel.btnProcess.setText("Process");
        if (msg.equals("CLOSE")) {

            System.out.println("Closing down app");
            for (int i = 3; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Closing in " + i);
                }
                catch ( InterruptedException e ) {
                    throw new RuntimeException(e);
                }
            }
            System.exit(0);
        }
        else {
            String[] msgComponents = msg.split(";");
            if (msgComponents[ 1 ].equals("M")) {
                switch (msgComponents[ 2 ]) {
                    case "Retrieve" -> {
                        selectedCDR = ExtractCDR(msgComponents);
                        ChangeCDRSelection(selectedCDR);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryScreen.responseMessage = "Item Retrieved";
                        _secondaryTablePanel.txtSection.setText(selectedCDR.getSection());
                        _secondaryTablePanel.txtBarcode.setText(selectedCDR.getBarCode());
                        _secondaryScreen.rebuildListsAndDisplay(LocalDataManagement
                                .searchByString(selectedCDR.getTitle()));
                    }
                    case "Add" -> {
                        String generatedBarcode = generateUniqueBarcode(barcodeLength);
                        _secondaryTablePanel.txtBarcode.setText(generatedBarcode);
                        _secondaryScreen.responseMessage = "New Item Added";
                        _secondaryTablePanel.txtSection.setEditable(true);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryTablePanel.btnProcess.setText("Add Item");
                    }
                    case "Return" -> {
                        selectedCDR = ExtractCDR(msgComponents);
                        ChangeCDRSelection(selectedCDR);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryScreen.responseMessage = "Item Returned";
                        _secondaryTablePanel.txtSection.setText(selectedCDR.getSection());
                        _secondaryTablePanel.txtBarcode.setText(selectedCDR.getBarCode());
                        _secondaryScreen.rebuildListsAndDisplay(LocalDataManagement
                                .searchByString(selectedCDR.getTitle()));
                    }
                    case "Remove" -> {
                        selectedCDR = ExtractCDR(msgComponents);
                        ChangeCDRSelection(selectedCDR);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryScreen.responseMessage = "Item Removed";
                        _secondaryTablePanel.txtSection.setText(selectedCDR.getSection());
                        _secondaryTablePanel.txtBarcode.setText(selectedCDR.getBarCode());
                        _secondaryScreen.rebuildListsAndDisplay(LocalDataManagement
                                .searchByString(selectedCDR.getTitle()));
                    }
                    case "Mostly Sorted", "Random Collection Sort" -> {
                        selectedCDR = ExtractCDR(msgComponents);
                        ChangeCDRSelection(selectedCDR);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryTablePanel.txtSection.setText(selectedCDR.getSection());
                        _secondaryScreen.rebuildListsAndDisplay(LocalDataManagement.modelsToObjectArrayList(LocalDataManagement
                                .searchBySection(selectedCDR.getSection())));
                        _secondaryScreen.responseMessage = "Item Sorted";
                    }

                    case "Reverse Sorted" -> {
                        selectedCDR = ExtractCDR(msgComponents);
                        ChangeCDRSelection(selectedCDR);
                        _secondaryTablePanel.txtAction.setText(msgComponents[ 2 ]);
                        _secondaryTablePanel.txtSection.setText(selectedCDR.getSection());
                        _secondaryScreen.rebuildListsAndDisplay(LocalDataManagement.modelsToObjectArrayList(LocalDataManagement
                                .searchBySection(selectedCDR.getSection())));
                        _secondaryScreen.responseMessage = "Item Reverse Sorted";
                    }
                }
            }
            else {
            }
        }
    }


    private String generateUniqueBarcode(int barcodeLength) {
        StringBuilder barcode = new StringBuilder();
        while (true) {
            for (int i = 0; i < barcodeLength; i++) {
                int randomDigit = ThreadLocalRandom.current().nextInt(0, 10);
                barcode.append(randomDigit);
            }
            if (LocalDataManagement.searchByBarcode(barcode.toString()).size() == 0) {
                return barcode.toString();
            }
        }


    }

    private void ChangeCDRSelection(CDRModel selectedCDR) {
        _secondaryScreen.selectedCDR = selectedCDR;
    }

    private CDRModel ExtractCDR(String[] msg) {
        CDRModel sentCDR = new CDRModel();
        try {
            sentCDR.setBarCode(msg[ 3 ]);
            sentCDR.setId(Integer.parseInt(msg[ 4 ]));
            sentCDR.setTitle(msg[ 5 ]);
            sentCDR.setYAxis(Integer.parseInt(msg[ 6 ]));
            sentCDR.setXAxis(Integer.parseInt(msg[ 7 ]));
            sentCDR.setAuthor(msg[ 8 ]);
            sentCDR.setDescription(msg[ 9 ]);
            sentCDR.setOnLoan(Boolean.parseBoolean(msg[ 10 ]));
            sentCDR.setSection(msg[ 11 ]);

        }
        catch ( NumberFormatException e ) {
            throw new RuntimeException(e);
        }
        return sentCDR;
    }
}
