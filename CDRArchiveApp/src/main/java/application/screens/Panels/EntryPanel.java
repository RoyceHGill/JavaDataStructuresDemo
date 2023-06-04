//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application.screens.Panels;

import application.library.UIComponentLibrary;
import application.models.CDRModel;
import application.screens.MainScreen;
import application.services.Configuration;
import application.services.LocalDataManagement;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public
class EntryPanel extends JPanel {
    public JButton btnNew, btnSaveEdit;
    public JLabel lblAuthor, lblDescription, lblSection, lblX, lblY, lblBarcode, lblTitle, lblID;
    public JTextField txtTitle, txtBarcode, txtY, txtAuthor, txtX, txtSection, txtID;
    public JTextArea txtDescription;

    public boolean isNewEntry = true;

    SpringLayout myLayout = new SpringLayout();

    ArrayList<CDRModel> CDRModels;

    private final MainScreen _main;


    public EntryPanel(ActionListener listener, MainScreen main) {
        setBackground(Configuration.panelBackgroundColour);
        setLayout(myLayout);
        setBorder(BorderFactory.createLineBorder(Color.black));
        placeAnchorComponents(listener);
        _main = main;
        CDRModels = _main.CDRModels;
        setupNewEntry();
    }

    private void placeAnchorComponents(ActionListener listener) {
        btnNew = UIComponentLibrary.createAButton("New Entry", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 30, 270, listener, this, myLayout);
        Configuration.applyButtonStyles(btnNew);

        btnSaveEdit = UIComponentLibrary.createAButton("Save/Edit", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 210, 270, listener, this, myLayout);
        Configuration.applyButtonStyles(btnSaveEdit);

        lblTitle = UIComponentLibrary.createALabel("Title: ", Configuration.dataEntryMargin, 20, this, myLayout);
        Configuration.applyLabelStyles(lblTitle);
        placeRelativeComponents(lblTitle);
    }


    private void placeRelativeComponents(JLabel title) {
        lblID = UIComponentLibrary.createALabel("ID: ", 248, 0, this, myLayout, title);
        Configuration.applyLabelStyles(lblID);

        lblAuthor = UIComponentLibrary.createALabel("Author: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, title);
        Configuration.applyLabelStyles(lblAuthor);

        lblSection = UIComponentLibrary.createALabel("Section: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, lblAuthor);
        Configuration.applyLabelStyles(lblSection);

        lblX = UIComponentLibrary.createALabel("X: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, lblSection);
        Configuration.applyLabelStyles(lblX);

        lblY = UIComponentLibrary.createALabel("Y: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, lblX);
        Configuration.applyLabelStyles(lblY);

        lblBarcode = UIComponentLibrary.createALabel("Barcode: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, lblY);
        Configuration.applyLabelStyles(lblBarcode);

        lblDescription = UIComponentLibrary.createALabel("Description: ", 0, Configuration.dataEntryLabelSpacing, this, myLayout, lblBarcode);
        Configuration.applyLabelStyles(lblDescription);


        txtTitle = UIComponentLibrary.createATextField(12, Configuration.dataEntryTextComponentMargin, 0, this, myLayout, title);
        Configuration.applyTextFieldStyles(txtTitle);

        txtID = UIComponentLibrary.createATextField(2, 19, -1, this, myLayout, lblID);
        Configuration.applyTextFieldStyles(txtID);
        txtID.setEditable(false);
        txtID.setForeground(Color.BLACK);
        txtAuthor = UIComponentLibrary.createATextField(12, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtTitle);
        Configuration.applyTextFieldStyles(txtAuthor);

        txtSection = UIComponentLibrary.createATextField(6, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtAuthor);
        Configuration.applyTextFieldStyles(txtSection);

        txtX = UIComponentLibrary.createATextField(6, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtSection);
        Configuration.applyTextFieldStyles(txtX);

        txtY = UIComponentLibrary.createATextField(6, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtX);
        Configuration.applyTextFieldStyles(txtY);

        txtBarcode = UIComponentLibrary.createATextField(12, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtY);
        Configuration.applyTextFieldStyles(txtBarcode);


        txtDescription = UIComponentLibrary.createATextArea(18, 3, 0, Configuration.dataEntryLabelSpacing, this, myLayout, txtBarcode);
        txtDescription.setBorder(BorderFactory.createLineBorder(Configuration.highlightColor, 2, false));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

    }

    public void updateEntryFields(ArrayList<CDRModel> cdrModels) {
        CDRModels = cdrModels;
        txtID.setText(Integer.toString(_main.selectedCDR.getId()));
        txtTitle.setText(_main.selectedCDR.getTitle());
        txtAuthor.setText(_main.selectedCDR.getAuthor());
        txtSection.setText(_main.selectedCDR.getSection());
        txtX.setText(Integer.toString(_main.selectedCDR.getXAxis()));
        txtY.setText(Integer.toString(_main.selectedCDR.getYAxis()));
        txtBarcode.setText(_main.selectedCDR.getBarCode());
        txtDescription.setText(_main.selectedCDR.getDescription());

        isNewEntry = false;
    }

    public void setupNewEntry() {
        _main.selectedCDR = new CDRModel();
        txtID.setText(Integer.toString(CDRModels.size() + 1));
        txtTitle.setText("");
        txtAuthor.setText("");
        txtSection.setText("");
        txtX.setText("");
        txtY.setText("");
        txtBarcode.setText("");
        txtDescription.setText("");
        isNewEntry = true;
    }

    public ArrayList<CDRModel> applyChanges(CDRModel selectedCDRModel) {
        ArrayList<CDRModel> oldCDRModels = LocalDataManagement.readCDRStorage();
        sanitizeTextBoxes();
        if (isNewEntry) {
            oldCDRModels.add(getCdrModel());
            CDRModels = oldCDRModels;
        }
        else {
            ArrayList<CDRModel> newCDRModels = new ArrayList<>();
            int id = (selectedCDRModel.getId());
            int sizeOldList = oldCDRModels.size();
            for (int i = 0; i < sizeOldList; i++) {
                if (id == oldCDRModels.get(i).getId()) {
                    newCDRModels.add(new CDRModel(
                            Integer.parseInt(txtID.getText()),
                            txtTitle.getText().replace(";", ""),
                            txtAuthor.getText().replace(";", ""),
                            txtSection.getText().replace(";", ""),
                            Integer.parseInt(txtX.getText().replace(";", "")),
                            Integer.parseInt(txtY.getText().replace(";", "")),
                            txtBarcode.getText().replace(";", ""),
                            txtDescription.getText().replace(";", ""),
                            selectedCDRModel.getOnLoan())
                    );
                }
                else {
                    newCDRModels.add(oldCDRModels.get(i));
                }
            }
            CDRModels = newCDRModels;
        }

        return CDRModels;


    }

    private void sanitizeTextBoxes() {
        txtTitle.setText(txtTitle.getText().replace(";", ""));
        txtAuthor.setText(txtAuthor.getText().replace(";", ""));
        txtBarcode.setText(txtBarcode.getText().replace(";", ""));
        txtX.setText(txtX.getText().replace(";", ""));
        txtY.setText(txtY.getText().replace(";", ""));
        txtDescription.setText(txtDescription.getText().replace(";", ""));
        txtSection.setText(txtSection.getText().replace(";", ""));
    }

    public boolean validateCDREntryFields() {
        if ("".equals(txtID.getText())
                || "".equals(txtTitle.getText())
                || "".equals(txtBarcode.getText())
                || "".equals(txtAuthor.getText())
                || "".equals(txtX.getText())
                || "".equals(txtY.getText())
                || "".equals(txtDescription.getText())
                || "".equals(txtSection.getText())) {
            JOptionPane.showMessageDialog(null, "Please enter a value for each field.");
            return false;
        }
        try {
            int validateInt = Integer.parseInt(txtX.getText());
            validateInt = Integer.parseInt(txtY.getText());

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "X and Y Fields only accept numbers.");
            return false;
        }
        return true;
    }

    public CDRModel getCdrModel() {
        return new CDRModel(
                Integer.parseInt(txtID.getText()),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtSection.getText(),
                Integer.parseInt(txtX.getText()),
                Integer.parseInt(txtY.getText()),
                txtBarcode.getText(),
                txtDescription.getText());
    }

    public void setupNewItemDetails(String[] msgComponents) {
        setupNewEntry();
        txtBarcode.setText(msgComponents[3]);
        txtSection.setText(msgComponents[11]);
        txtX.setText(msgComponents[7]);
        txtY.setText(msgComponents[6]);
    }
}
