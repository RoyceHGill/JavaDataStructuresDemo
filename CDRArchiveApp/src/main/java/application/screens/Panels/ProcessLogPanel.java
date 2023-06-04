package application.screens.Panels;

import application.library.DoublyLinkedList;
import application.library.UIComponentLibrary;
import application.services.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProcessLogPanel extends JPanel {
    public JButton btnProcessLog, btnPreOrder, btnInOrder, btnPostOrder, btnGraphical, btnSaveHashMap, btnDisplay;
    public JTextArea txtConsole;
    public JLabel lblConsole, lblBinaryTree, lblHashmap;
    public JButton activeButton;

    SpringLayout springLayout = new SpringLayout();

    public ProcessLogPanel(ActionListener actionListener) {
        setLayout(springLayout);
        placeAnchorComponents(actionListener);
        setBackground(Configuration.panelBackgroundColour);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    private void placeAnchorComponents(ActionListener actionListener) {
        lblBinaryTree = UIComponentLibrary.createALabel("Binary Tree",

                170, 5, this, springLayout);
        Configuration.applyLabelStyles(lblBinaryTree);

        lblHashmap = UIComponentLibrary.createALabel("HashMap", 505, 5, this, springLayout);
        Configuration.applyLabelStyles(lblHashmap);

        lblConsole = UIComponentLibrary.createALabel("Console:", 10, 57, this, springLayout);
        Configuration.applyLabelStyles(lblConsole);

        placeRelativeComponents(actionListener);

    }

    private void placeRelativeComponents(ActionListener actionListener) {
        btnPreOrder = UIComponentLibrary.createAButton("Pre-Order", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, -160, 25, actionListener, this, springLayout, lblBinaryTree);
        Configuration.applyButtonStyles(btnPreOrder);

        btnPostOrder = UIComponentLibrary.createAButton("Post-Order", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, Configuration.defaultButtonWidth + 5, 0, actionListener, this, springLayout, btnPreOrder);
        Configuration.applyButtonStyles(btnPostOrder);

        btnInOrder = UIComponentLibrary.createAButton("In-Order", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, Configuration.defaultButtonWidth + 5, 0, actionListener, this, springLayout, btnPostOrder);
        Configuration.applyButtonStyles(btnInOrder);

        btnGraphical = UIComponentLibrary.createAButton("Graphical", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, Configuration.defaultButtonWidth + 5, 0, actionListener, this, springLayout, btnInOrder);
        Configuration.applyButtonStyles(btnGraphical);

        btnSaveHashMap = UIComponentLibrary.createAButton("Save", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, -70, 25, actionListener, this, springLayout, lblHashmap);
        Configuration.applyButtonStyles(btnSaveHashMap);

        btnDisplay = UIComponentLibrary.createAButton("Display", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, Configuration.defaultButtonWidth + 5, 0, actionListener, this, springLayout, btnSaveHashMap);
        Configuration.applyButtonStyles(btnDisplay);

        txtConsole = UIComponentLibrary.createATextArea(55, 8, 0, 0, this, springLayout, lblConsole);
        txtConsole.setEditable(false);
        Configuration.applyTextFieldStyles(txtConsole);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(txtConsole);
        springLayout.putConstraint("West", scrollPane, 1, "West", lblConsole);
        springLayout.putConstraint("North", scrollPane, 23, "North", lblConsole);
        scrollPane.setSize(new Dimension(638, 176));
        add(scrollPane);
        scrollPane.setVisible(true);

        btnProcessLog = UIComponentLibrary.createAButton("Process Log", Configuration.defaultButtonWidth, Configuration.defaultButtonHeight, 100, 0, actionListener, this, springLayout, lblConsole);
        Configuration.applyButtonStyles(btnProcessLog);
    }

    public void populateProcessLog(DoublyLinkedList processLog) {
        txtConsole.setText("");
        int size = processLog.getSize();
        for (int i = 0; i < size; i++) {
            txtConsole.append(processLog.get(i).toString());
            txtConsole.append("\n");
        }
    }
}
