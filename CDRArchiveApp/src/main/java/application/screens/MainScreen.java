package application.screens;

import application.library.BinaryTree;
import application.library.DoublyLinkedList;
import application.library.IApp;
import application.models.CDRModel;
import application.models.ProcessLogRecord;
import application.screens.Panels.ActionPanel;
import application.screens.Panels.EntryPanel;
import application.screens.Panels.DataTablePanel;
import application.screens.Panels.ProcessLogPanel;
import application.services.CDRArchiveClient;
import application.services.Configuration;
import application.services.LocalDataManagement;
import application.services.Sorts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Application window for the Archive application.
 */
public class MainScreen extends JFrame implements MouseListener, ActionListener, IApp {
    public ArrayList<CDRModel> CDRModels = LocalDataManagement.readCDRStorage();
    public DataTablePanel dataTablePanel = new DataTablePanel(this, this);
    public EntryPanel crudPanel = new EntryPanel(this, this);
    public ProcessLogPanel processLogPanel = new ProcessLogPanel(this);
    public DoublyLinkedList ProcessLogDLL = new DoublyLinkedList();
    public CDRModel selectedCDR = new CDRModel();
    public ArrayList<Object[]> CDRModelsAsArrayOfObjects = LocalDataManagement.modelsToObjectArrayList(CDRModels);
    SpringLayout layout = new SpringLayout();
    ActionPanel actionPanel = new ActionPanel(this);
    CDRArchiveClient networkClient;
    ArrayList<CDRModel> CDRModelsBinaryTree = new ArrayList<>();
    public String outMessage;

    public MainScreen() {
        getContentPane().setBackground(Configuration.backgroundColour);
        setSize(Configuration.screenDimension);
        setLayout(layout);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        networkClient = new CDRArchiveClient(this);

        buildPanels();
        setVisible(true);
        MainScreen.setLookAndFeel();
    }

    /**
     * Set the look and feel attribute of the UI manger to the System's Look and feel.
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException |
               ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates, positions and sizes the Panels of the UI
     */
    private void buildPanels() {
        layout.putConstraint("West", dataTablePanel, 20, "West", this);
        layout.putConstraint("North", dataTablePanel, 20, "North", this);
        dataTablePanel.setPreferredSize(new Dimension(663, 255));
        add(dataTablePanel);

        layout.putConstraint("West", crudPanel, 700, "West", this);
        layout.putConstraint("North", crudPanel, 20, "North", this);
        crudPanel.setPreferredSize(new Dimension(330, 300));
        add(crudPanel);

        layout.putConstraint("West", actionPanel, 700, "West", this);
        layout.putConstraint("North", actionPanel, 330, "North", this);
        actionPanel.setPreferredSize(new Dimension(330, 220));
        add(actionPanel);

        layout.putConstraint("West", processLogPanel, 20, "West", this);
        layout.putConstraint("North", processLogPanel, 280, "North", this);
        processLogPanel.setPreferredSize(new Dimension(663, 270));
        add(processLogPanel);
    }

    /**
     * The Action listener Method that will trigger when an action is performed within the Action Listeners scope.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //requires if statement

        if (e.getSource() == dataTablePanel.btnSortByTitle
                || e.getSource() == processLogPanel.btnInOrder
                || e.getSource() == processLogPanel.btnPreOrder
                || e.getSource() == processLogPanel.btnPostOrder) {
            int titleIndex = 1;
            updateListsAndTable(Sorts
                    .alphabeticalTableModelBubbleSort(CDRModelsAsArrayOfObjects, titleIndex));
        }

        // Can be in switch
        switch (e.getActionCommand()) {
            case "Search" -> {
                updateListsAndTable(LocalDataManagement
                        .searchByString(dataTablePanel
                                .txtSearchField.getText()));
            }
            case "New Entry" -> {
                refreshDataAndEntries();
            }
            case "Save/Edit" -> {
                saveCDR();
            }
            case "Retrieve", "Return", "Remove" -> {
                if (0 != selectedCDR.getId()) {
                    String label = e.getActionCommand();
                    CDRModel toBeSent = selectedCDR;
                    networkClient.send(toBeSent, label);
                    updateProcessLog(label);
                    JOptionPane.showMessageDialog(null, "The Arm will now " + label + " the cdr.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "No CDR Selected");
                }
            }
            case "Add" -> {
                String label = e.getActionCommand();
                CDRModel toBeSent = selectedCDR;
                networkClient.send(toBeSent, label);
                updateProcessLog(label);
                JOptionPane.showMessageDialog(null, "The Arm will return with your storage details.");
            }
            case "by Author" -> {
                int authorIndex = 2;
                updateListsAndTable(Sorts
                        .ascendingTableModelInsertionSort(CDRModelsAsArrayOfObjects, authorIndex));
            }
            case "by Barcode" -> {
                int barcodeIndex = 6;
                updateListsAndTable(Sorts
                        .numericalAscendingMergeSort(CDRModelsAsArrayOfObjects, barcodeIndex));
            }
            case "Process Log" -> {
                processLogPanel.populateProcessLog(ProcessLogDLL);
            }
            case "In-Order" -> {
                clearProcessLog();
                BinaryTree binaryTree = createProcessLogBinaryTree();
                CDRModelsBinaryTree = binaryTree.getInOrderTraversalList();
                displayCDRModelsToProcessLog(CDRModelsBinaryTree);
            }
            case "Pre-Order" -> {
                clearProcessLog();
                BinaryTree binaryTree = createProcessLogBinaryTree();
                CDRModelsBinaryTree = binaryTree.getPreOrderTraversalList();
                displayCDRModelsToProcessLog(CDRModelsBinaryTree);
            }
            case "Post-Order" -> {
                clearProcessLog();
                BinaryTree binaryTree = createProcessLogBinaryTree();
                CDRModelsBinaryTree = binaryTree.getPostOrderTraversalList();
                displayCDRModelsToProcessLog(CDRModelsBinaryTree);
            }
            case "Save" -> {
                HashMap<String, String> binaryTreeHapMap = new HashMap<String, String>();
                for (CDRModel cd : CDRModelsBinaryTree
                ) {
                    binaryTreeHapMap.put(cd.getBarCode(), cd.getTitle());
                }
                LocalDataManagement.writeHashMapToFile(binaryTreeHapMap);
            }
            case "Display" -> {
                HashMap<String, String> binaryTreeHashMap = new HashMap<String, String>();
                for (CDRModel cd : CDRModelsBinaryTree
                ) {
                    binaryTreeHashMap.put(cd.getBarCode(), cd.getTitle());
                }
                processLogPanel.txtConsole.setText(binaryTreeHashMap.toString()
                        .replace(", ", ",\n  ")
                        .replace("{", "{\n  ")
                        .replace("}", "\n}"));
            }
            case "Random Collection Sort", "Mostly Sorted", "Reverse Sorted" -> {
                if (validateSection(actionPanel.txtSection)) {
                    String label = e.getActionCommand();
                    CDRModel toBeSent = new CDRModel();
                    toBeSent.setSection(actionPanel.txtSection.getText());
                    networkClient.send(toBeSent, label);
                    updateProcessLog(label);
                    JOptionPane.showMessageDialog(null, "Message sent to Arm to Sort the Repository.");
                }
            }
        }
    }

    private boolean validateSection(JTextField txtSection) {
        String section = txtSection.getText();
        if ("".equals(section)) {
            JOptionPane.showMessageDialog(null, "Can not send message without Section.");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Takes in a Type of process as a string, The Process log Doubly linked list is then appended to with the new process log.
     *
     * @param type String Example: Item Retrieved
     */
    public void updateProcessLog(String type) {
        ProcessLogDLL.append(
                new ProcessLogRecord(LocalDateTime.now(), "SENT",
                        type + " Item", selectedCDR.getBarCode()));
        processLogPanel.populateProcessLog(ProcessLogDLL);
    }

    /**
     * Appends an already constructed process log object to the doubly linked list and then displays it to the screen.
     *
     * @param log Pre-Constructed Process log object
     */
    public void updateProcessLog(ProcessLogRecord log) {
        ProcessLogDLL.append(log);
        processLogPanel.populateProcessLog(ProcessLogDLL);
    }

    /**
     * loops through a list of CDR models to display them to the process log.
     *
     * @param cdrModels CDR Array List
     */
    private void displayCDRModelsToProcessLog(ArrayList<CDRModel> cdrModels) {
        for (CDRModel CDR : cdrModels
        ) {
            processLogPanel.txtConsole.append(CDR.toBarcodeString());
            processLogPanel.txtConsole.append("\n");
        }
    }

    /**
     * Creates a Binray tree object and adds the CDR Models from the primary List to it.
     *
     * @return A binary tree object with all CDR object contained.
     */
    private BinaryTree createProcessLogBinaryTree() {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.addManyNodes(CDRModels);
        return binaryTree;
    }

    /**
     * Removes all text from the process log console.
     */
    private void clearProcessLog() {
        processLogPanel.txtConsole.setText("");
    }

    /**
     * Saves the current text field entries into the CSV.
     */
    public void saveCDR() {
        if (crudPanel.validateCDREntryFields()) {
            CDRModels = crudPanel.applyChanges(selectedCDR);

            LocalDataManagement.writeCDRStorage(CDRModels, dataTablePanel.headers);
            CDRModelsAsArrayOfObjects = LocalDataManagement.modelsToObjectArrayList(CDRModels);
            refreshDataAndEntries();
        }
    }

    /**
     * Updates the On loan property of the CDR contained in the message as false to signify that the CDR has been returned.
     *
     * @param msgComponents The Message string from the robot arm.
     */
    public void markCDRReturned(String[] msgComponents) {
        if (validateMessage(msgComponents)) {
            selectedCDR = CDRModels.get(Integer.parseInt(msgComponents[4]) - 1);
            selectedCDR.setOnLoan(false);
            crudPanel.isNewEntry = false;
            saveCDR();
        }
    }

    /**
     * Updates the On loan property of the CDR contained within the message as true to signify that the CDR has been loaded out.
     *
     * @param msgComponents The message String from the robot arm.
     */
    public void markCDRRetrieved(String[] msgComponents) {
        if (validateMessage(msgComponents)) {
            selectedCDR = CDRModels.get(Integer.parseInt(msgComponents[4]) - 1);
            selectedCDR.setOnLoan(true);
            crudPanel.isNewEntry = false;
            saveCDR();
        }

    }

    public void removeCDR(String[] msgComponents) {
        if (validateMessage(msgComponents)) {
            selectedCDR = CDRModels.get(Integer.parseInt(msgComponents[4]) - 1);
            CDRModels.remove(Integer.parseInt(msgComponents[4]) - 1);
            LocalDataManagement.writeCDRStorage(CDRModels, LocalDataManagement.getTableHeaders());
            refreshDataAndEntries();
        }
    }

    private boolean validateMessage(String[] msgComponents) {
        return null != msgComponents[4];

    }

    /**
     * Add the Barcode, X , Y and Section data from the message to the text fields of the Entry panel.
     *
     * @param msgComponents Message from the robot arm.
     */
    public void setupItemToAdd(String[] msgComponents) {
        selectedCDR = new CDRModel();
        crudPanel.setupNewItemDetails(msgComponents);
    }

    /**
     * Refresh UI elements with data from file and clear data entries.
     */
    public void refreshDataAndEntries() {
        CDRModels = LocalDataManagement.readCDRStorage();
        crudPanel.setupNewEntry();
        dataTablePanel.refreshPanel();
    }

    /**
     * Updates the global Collections from the arrayList of object and updates the table.
     *
     * @param arrayList The ArrayList of objects that are equal to the properties of the CDR object.
     */
    private void updateListsAndTable(ArrayList<Object[]> arrayList) {
        CDRModelsAsArrayOfObjects = arrayList;
        CDRModels = LocalDataManagement.objectArrayListToModels(CDRModelsAsArrayOfObjects);
        dataTablePanel.tableModel.updateTableModels(CDRModelsAsArrayOfObjects);
    }

    /**
     * Fires when the mouse is pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int i = dataTablePanel.table.getSelectedRow();
        selectedCDR = CDRModels.get(i);
        crudPanel.updateEntryFields(CDRModels);
    }

    /**
     * Fires when the mouse is clicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Fires when a mouse click is released.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Fires when a mouse enters the component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Fires when the mouse exits this component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * The method that will fire at startup.
     */
    public void start() {

    }

    /**
     * The method that will fire for a graceful shutdown.
     */
    public void shutDown() {

        outMessage = "Shutdown is called";
        System.out.println(outMessage);
        try {
            System.out.println("Shutting down Network");
            networkClient.send("CLOSE");
            Thread.sleep(5000);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
