//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application.screens.Panels;

import application.library.UIComponentLibrary;
import application.models.CDRModel;
import application.services.Configuration;
import application.services.LocalDataManagement;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.AbstractTableModel;

public class DataTablePanel extends JPanel {
    public JButton btnSearch;
    public JButton btnSortByTitle;
    public JButton btnSortByAuthor;
    public JButton btnSortByBarcode;
    public JTextField txtSearchField;
    public JLabel lblSearch;
    public JLabel lblSort;
    public JTable table;
    ArrayList<CDRModel> CDRModelArrayList = new ArrayList<>(LocalDataManagement.readCDRStorage());
    ArrayList<Object[]> CdrObjectArrayList = LocalDataManagement.modelsToObjectArrayList(CDRModelArrayList);
    public String[] headers = LocalDataManagement.getTableHeaders();
    public TableModel tableModel = new TableModel(CdrObjectArrayList, headers);
    SpringLayout myLayout = new SpringLayout();

    public DataTablePanel(ActionListener actionListener, MouseListener mouseListener) {
        setLayout(myLayout);
        placeAnchorComponents(actionListener, mouseListener);
        setBackground(Configuration.panelBackgroundColour);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void placeAnchorComponents(ActionListener listener, MouseListener mouseListener) {
        buildTable(mouseListener);
        lblSearch = UIComponentLibrary.createALabel("Search:", 30, 10, this, myLayout);
        Configuration.applyLabelStyles(lblSearch);
        lblSort = UIComponentLibrary.createALabel("Sort: ", 305, 10, this, myLayout);
        Configuration.applyLabelStyles(lblSort);
        placeRelativeComponents(listener);
    }

    private void placeRelativeComponents(ActionListener listener) {
        txtSearchField = UIComponentLibrary.createATextField(10, 50, -1, this, myLayout, lblSearch);
        txtSearchField.setFont(Configuration.defaultFont);
        txtSearchField.setBorder(BorderFactory.createLineBorder(Configuration.highlightColor, 2, false));

        btnSearch = UIComponentLibrary.createAButton("Search", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 125, 0, listener, this, myLayout, txtSearchField);
        Configuration.applyButtonStyles(btnSearch);

        btnSortByTitle = UIComponentLibrary.createAButton("by Title", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 30, -1, listener, this, myLayout, lblSort);
        Configuration.applyButtonStyles(btnSortByTitle);
        btnSortByAuthor = UIComponentLibrary.createAButton("by Author", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 130, -1, listener, this, myLayout, lblSort);
        Configuration.applyButtonStyles(btnSortByAuthor);
        btnSortByBarcode = UIComponentLibrary.createAButton("by Barcode", 90, Configuration.defaultButtonHeight, 230, -1, listener, this, myLayout, lblSort);
        Configuration.applyButtonStyles(btnSortByBarcode);
    }

    private void buildTable(MouseListener mouseListener) {
        table = new JTable(tableModel);
        table.isForegroundSet();
        table.setShowHorizontalLines(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setFont(Configuration.defaultFont);
        table.setAutoResizeMode(0);
        table.getColumnModel().getColumn(0).setMaxWidth(23);
        table.getColumnModel().getColumn(1).setMinWidth(130);
        table.getColumnModel().getColumn(3).setMaxWidth(47);
        table.getColumnModel().getColumn(4).setMaxWidth(23);
        table.getColumnModel().getColumn(5).setMaxWidth(23);
        table.getColumnModel().getColumn(7).setMinWidth(185);
        table.getColumnModel().getColumn(8).setMaxWidth(50);
        table.setBackground(Configuration.primaryColour);
        table.setForeground(Configuration.highlightColor);
        table.getTableHeader().setBackground(Configuration.primaryColour);
        table.getTableHeader().setForeground(Configuration.highlightColor);
        table.addMouseListener(mouseListener);
        table.getTableHeader().setReorderingAllowed(false);
        add(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(650, 210));
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        add(scrollPane, "Center");
        scrollPane.getViewport().setBackground(Configuration.primaryColour);
        myLayout.putConstraint("West", scrollPane, 5, "West", this);
        myLayout.putConstraint("North", scrollPane, 35, "North", this);
    }

    public void refreshPanel() {

        txtSearchField.setText("");
        CdrObjectArrayList = LocalDataManagement.searchByString(txtSearchField.getText());
        tableModel.updateTableModels(CdrObjectArrayList);
    }

    public class TableModel extends AbstractTableModel {
        String[] header;
        ArrayList<Object[]> CdrObjectArrayList;
        int loanCol;

        TableModel(ArrayList<Object[]> cdrList, String[] headers) {
            header = headers;
            CdrObjectArrayList = cdrList;
            loanCol = findColumn("OnLoan");
        }

        public void updateTableModels(ArrayList<Object[]> cdrList) {
            CdrObjectArrayList = cdrList;
            loanCol = findColumn("OnLoan");
            fireTableDataChanged();
            table.repaint();
        }

        public int getRowCount() {
            return CdrObjectArrayList.size();
        }

        public int getColumnCount() {
            return header.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return ((Object[]) CdrObjectArrayList.get(rowIndex))[columnIndex];
        }

        public String getColumnName(int index) {
            return header[index];
        }

        public Class getColumnClass(int columnIndex) {
            return columnIndex == loanCol ? Boolean.class : super.getColumnClass(columnIndex);
        }

    }
}
