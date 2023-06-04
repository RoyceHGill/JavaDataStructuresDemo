//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package SecondaryApplication.Screens.Panels;

import SecondaryApplication.Library.UIComponentLibrary;
import SecondaryApplication.Models.CDRModel;
import SecondaryApplication.Services.Configuration;
import SecondaryApplication.Services.LocalDataManagement;
import SecondaryApplication.Services.SecondaryScreenConfiguration;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SecondaryTablePanel extends JPanel {
    SpringLayout myLayout = new SpringLayout();
    public String[] headers = LocalDataManagement.getTableHeaders();
    ArrayList<CDRModel> CDRModelArrayList = new ArrayList<>(LocalDataManagement.readFileData());
    ArrayList<Object[]> CdrObjectArrayList = LocalDataManagement.modelsToObjectArrayList(CDRModelArrayList);
    public TableModel tableModel = new TableModel(CdrObjectArrayList, headers);
    public String[] actions = {"Add", "Remove", "Retrieve", "Return", "Sort"};
    public JTextField txtAction;
    public JTable table;
    public JButton btnExit, btnProcess;
    public JTextField txtBarcode, txtSection;
    public JLabel lblCurrentAction, lblBarcode, lblSection;


    public SecondaryTablePanel(ActionListener actionListener) {
        setLayout(this.myLayout);
        placeAnchorComponents(actionListener);
        setBackground(SecondaryScreenConfiguration.panelBackgroundColour);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void placeAnchorComponents(ActionListener listener) {
        BuildTable();
        lblCurrentAction = UIComponentLibrary.createALabel("Current Action: ", 10,10,this,myLayout);
        Configuration.applyLabelStyles(lblCurrentAction);

        placeRelativeComponents(listener);
    }

    private void placeRelativeComponents(ActionListener listener) {
        txtAction = UIComponentLibrary.createATextField(6, 90, 0, this,myLayout,lblCurrentAction);
        txtAction.setEditable(false);

        lblBarcode = UIComponentLibrary.createALabel("Barcode: ", 175, 0, this,myLayout, lblCurrentAction);
        lblSection = UIComponentLibrary.createALabel("Section: ", 120, 0, this, myLayout, lblBarcode);

        txtBarcode = UIComponentLibrary.createATextField(6, 50, 0, this,myLayout,lblBarcode);
        txtBarcode.setEditable(false);
        txtSection = UIComponentLibrary.createATextField(2, 45, 0 , this, myLayout, lblSection);
        txtSection.setEditable(false);

        btnProcess = UIComponentLibrary.createAButton("Process", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight,
                380, 0, listener,this,myLayout, lblCurrentAction);
        btnExit = UIComponentLibrary.createAButton("Exit", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight,
                580, 0, listener,this,myLayout,lblCurrentAction);

        Configuration.applyButtonStyles(btnProcess);
        Configuration.applyButtonStyles(btnExit);

        Configuration.applyTextFieldStyles(txtBarcode);
        Configuration.applyTextFieldStyles(txtSection);

        Configuration.applyLabelStyles(lblBarcode);
        Configuration.applyLabelStyles(lblSection);
    }


    private void BuildTable() {
        table = new JTable(this.tableModel);
        table.isForegroundSet();
        table.setShowHorizontalLines(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setFont(SecondaryScreenConfiguration.defaultFont);
        table.setAutoResizeMode(0);
        table.getColumnModel().getColumn(0).setMaxWidth(23);
        table.getColumnModel().getColumn(1).setMinWidth(130);
        table.getColumnModel().getColumn(3).setMaxWidth(47);
        table.getColumnModel().getColumn(4).setMaxWidth(23);
        table.getColumnModel().getColumn(5).setMaxWidth(23);
        table.getColumnModel().getColumn(7).setMinWidth(185);
        table.getColumnModel().getColumn(8).setMaxWidth(50);
        table.setBackground(SecondaryScreenConfiguration.primaryColour);
        table.setForeground(SecondaryScreenConfiguration.highlightColor);
        table.getTableHeader().setBackground(SecondaryScreenConfiguration.primaryColour);
        table.getTableHeader().setForeground(SecondaryScreenConfiguration.highlightColor);
        table.getTableHeader().setReorderingAllowed(false);
        add(this.table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650, 210));
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        add(scrollPane, "Center");
        scrollPane.getViewport().setBackground(SecondaryScreenConfiguration.primaryColour);
        myLayout.putConstraint("West", scrollPane, 20, "West", this);
        myLayout.putConstraint("North", scrollPane, 40, "North", this);
    }
    public void setupNewEntry() {

        String s = "";
        CdrObjectArrayList = LocalDataManagement.searchByString(s);
        tableModel.updateTableModels(CdrObjectArrayList);
    }
    public class TableModel extends AbstractTableModel {
        String[] header;
        ArrayList<Object[]> CdrObjectArrayList;
        int loanCol;

        TableModel(ArrayList<Object[]> cdrList, String[] headers) {
            this.header = headers;
            CdrObjectArrayList = cdrList;
            this.loanCol = this.findColumn("OnLoan");
        }

        public void updateTableModels(ArrayList<Object[]> cdrList) {
            CdrObjectArrayList = cdrList;
            this.loanCol = this.findColumn("OnLoan");
            this.fireTableDataChanged();
            table.repaint();
        }

        public int getRowCount() {
            return CdrObjectArrayList.size();
        }

        public int getColumnCount() {
            return this.header.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return ((Object[])CdrObjectArrayList.get(rowIndex))[columnIndex];
        }

        public String getColumnName(int index) {
            return this.header[index];
        }

        public Class getColumnClass(int columnIndex) {
            return columnIndex == this.loanCol ? Boolean.class : super.getColumnClass(columnIndex);
        }

    }
}
