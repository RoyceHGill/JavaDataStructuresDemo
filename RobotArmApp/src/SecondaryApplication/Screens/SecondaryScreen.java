package SecondaryApplication.Screens;

import SecondaryApplication.Library.IApp;
import SecondaryApplication.Models.CDRModel;
import SecondaryApplication.Screens.Panels.SecondaryTablePanel;
import SecondaryApplication.Services.CDRArmClient;
import SecondaryApplication.Services.LocalDataManagement;
import SecondaryApplication.Services.SecondaryScreenConfiguration;
import SecondaryApplication.Services.Sorts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The Application Frame for the Simulated Robot Arm
 */
public class SecondaryScreen extends JFrame implements ActionListener, IApp {
    ArrayList<CDRModel> CDRModels = LocalDataManagement.readFileData();
    ArrayList<Object[]> CDRModelsAsArrayOfObjects = LocalDataManagement.modelsToObjectArrayList(CDRModels);
    SpringLayout layout = new SpringLayout();
    SecondaryTablePanel secondaryTablePanel = new SecondaryTablePanel(this);
    public String responseMessage = "";
    CDRArmClient networkClient = null;

    public CDRModel selectedCDR = new CDRModel();

    public SecondaryScreen() {
        setLookAndFeel();
        this.getContentPane().setBackground(SecondaryScreenConfiguration.backgroundColour);
        this.setSize(SecondaryScreenConfiguration.screenDimension);
        this.setLayout(layout);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        networkClient = new CDRArmClient(this.secondaryTablePanel,this);

        buildPanels();
        this.setVisible(true);
    }

    /**
     * Places Sizes and Styles the Panel for the components.
     */
    private void buildPanels() {
        layout.putConstraint("West", secondaryTablePanel, 9, "West", this);
        layout.putConstraint("North", secondaryTablePanel, 20, "North", this);
        secondaryTablePanel.setPreferredSize(new Dimension(690, 270));
        this.add(secondaryTablePanel);
    }

    /**
     * The Action Performed Event listener, Will fire when components within the Listeners scope are interacted with.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == secondaryTablePanel.btnProcess){
            switch (responseMessage) {
                case "Item Retrieved", "Item Returned", "Item Removed" -> {
                    networkClient.send(selectedCDR, responseMessage);
                    clearSelection();
                }
                case "New Item Added" -> {
                    if (validateSection(secondaryTablePanel.txtSection))
                    {
                        selectedCDR = new CDRModel();
                        selectedCDR.setSection(secondaryTablePanel.txtSection.getText());
                        selectedCDR.setBarCode(secondaryTablePanel.txtBarcode.getText());
                        int[] coordinates = generateXYPosForSection(selectedCDR.getSection());
                        // int[x,y]
                        selectedCDR.setXAxis(coordinates != null ? coordinates[0] : -1);
                        selectedCDR.setYAxis(coordinates != null ? coordinates[1] : -1);
                        if (selectedCDR.getXAxis() == -1 || selectedCDR.getYAxis() == -1) {
                            JOptionPane.showMessageDialog(null, "Error finding available position.");
                            break;
                        }

                        networkClient.send(selectedCDR, responseMessage);
                        clearSelection();
                        secondaryTablePanel.btnProcess.setText("Process");
                    }

                }
                case "Item Sorted" -> {
                    ArrayList<CDRModel> sectionList = LocalDataManagement.searchBySection(selectedCDR.getSection());
                    ArrayList<CDRModel> sortedByTitleSectionList = Sorts.mergeSortByTitle(sectionList);

                    for (CDRModel cd: sortedByTitleSectionList) {
                        cd.setXAxis(0);
                        cd.setYAxis(0);
                    }
                    for (CDRModel cd: sortedByTitleSectionList){
                        int[] co = generateXYPosForSection(sortedByTitleSectionList);
                        cd.setXAxis(co != null ? co[0] : -1);
                        cd.setYAxis(co != null ? co[1] : -1);
                        networkClient.send(cd, responseMessage);
                    }
                    //
                }
                case "Item Reverse Sorted" -> {
                    ArrayList<CDRModel> sectionList = LocalDataManagement.searchBySection(selectedCDR.getSection());
                    ArrayList<CDRModel> sortedByTitleSectionList = Sorts.reverseMergeSortByTitle(sectionList);

                    for (CDRModel cd: sortedByTitleSectionList) {
                        cd.setXAxis(0);
                        cd.setYAxis(0);
                    }
                    for (CDRModel cd: sortedByTitleSectionList){
                        int[] co = generateXYPosForSection(sortedByTitleSectionList);
                        cd.setXAxis(co != null ? co[0] : -1);
                        cd.setYAxis(co != null ? co[1] : -1);
                        networkClient.send(cd, responseMessage);
                    }
                    //
                }
            }
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            secondaryTablePanel.setupNewEntry();
        }
    }

    /**
     * Validates the Section property is valid.
     * @param txtSection
     * The Text field object
     * @return
     * true for valid false for invalid.
     */
    private boolean validateSection(JTextField txtSection) {
        String section = txtSection.getText();
        if (section.equals("")){
            JOptionPane.showMessageDialog(null, "Section is missing.");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Gets the next available X and Y position in the section.
     * @param section
     * The Section to be searched.
     * @return
     * A pair of Coordinates in the Repo. [X,Y]
     */
    private int[] generateXYPosForSection(String section) {
        ArrayList<CDRModel> sectionList = LocalDataManagement.searchBySection(section);
        for (int x = 1; x < 5 + 1; x++) {

            ArrayList<CDRModel> xFilteredList = LocalDataManagement.searchByX(sectionList, x);
            if (xFilteredList.size() == 0) {
                int[] ints = {1,1};
                return ints;
            }
            for (int y = 1; y < 70 + 1; y++) {
                ArrayList<CDRModel> yFilteredList = LocalDataManagement.searchByY(xFilteredList, y);
                if (yFilteredList.size() == 0) {
                    int[] ints = {x,y};
                    return ints;
                }
            }
        }
        return null;
    }

    /**
     * Finds the next available X and Y position in the Section
     * @param sectionList
     * Array list of objects for a Section.
     * @return
     * A pair of Coordinates [X,Y]
     */
    private int[] generateXYPosForSection(ArrayList<CDRModel> sectionList) {
        for (int x = 1; x < 5 + 1; x++) {
            ArrayList<CDRModel> xFilteredList = LocalDataManagement.searchByX(sectionList, x);
            if (xFilteredList.size() == 0) {
                int[] ints = {1,1};
                return ints;
            }
            for (int y = 1; y < 70 + 1; y++) {
                ArrayList<CDRModel> yFilteredList = LocalDataManagement.searchByY(xFilteredList, y);
                if (yFilteredList.size() == 0) {
                    int[] ints = {x,y};
                    return ints;
                }
            }
        }
        return null;
    }

    /**
     * Clears the Text fields and Selected CDr from memory.
     */
    private void clearSelection() {
        secondaryTablePanel.txtBarcode.setText("");
        secondaryTablePanel.txtSection.setText("");
        secondaryTablePanel.txtAction.setText("");
        selectedCDR = new CDRModel();
    }

    /**
     * Fires when shut down to preform a graceful shutdown.
     */
    public void shutDown() {
        // Do a graceful shutdown here
        System.out.println("Shutdown is called");
        try {
            System.out.println("Shutting down Network");
            networkClient.send("CLOSE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fires at start up.
     */
    public void start() { }

    /**
     * Changes the UIMangers look and feel attribute to be the same and the SystemLook and feel.
     */
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the Array lists to be Equal to the given array list of objects and updates the Table to display.
     * @param arrayList
     * Array list of object that are properties of a CDR Object.
     */
    public void rebuildListsAndDisplay(ArrayList<Object[]> arrayList) {
        CDRModelsAsArrayOfObjects = arrayList;
        CDRModels = LocalDataManagement.objectArrayListToModels(CDRModelsAsArrayOfObjects);
        secondaryTablePanel.tableModel.updateTableModels(CDRModelsAsArrayOfObjects);
    }
}
