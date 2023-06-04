package application.screens.Panels;

import application.library.UIComponentLibrary;
import application.services.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {
    public JLabel lblRequests, lblSortSection;
    public JButton btnRetrieve, btnRemove, btnReturn, btnAdd , btnRandom, btnMostlySorted, btnReverseSort;
    public JTextField txtSection;


    SpringLayout springLayout = new SpringLayout();
    public ActionPanel(ActionListener actionListener)
    {
        setBackground(Configuration.panelBackgroundColour);
        setLayout(springLayout);
        setBorder(BorderFactory.createLineBorder(Color.black));
        placeAnchorComponents(actionListener);
    }

    private void placeAnchorComponents(ActionListener actionListener) {
        lblRequests = UIComponentLibrary.createALabel(
                "Automation Action Request for Selected Item",
                40,10, this, springLayout);
        Configuration.applyLabelStyles(lblRequests);
        placeRelativeComponents(lblRequests, actionListener);
    }

    private void placeRelativeComponents(JLabel lblRequests, ActionListener actionListener) {
        btnRetrieve = UIComponentLibrary.createAButton("Retrieve",
                Configuration.defaultButtonWidthLarge, Configuration.defaultButtonHeight,
                -10, 40, actionListener,this, springLayout,
                lblRequests);
        Configuration.applyButtonStyles(btnRetrieve);

        lblSortSection = UIComponentLibrary.createALabel("Sort Section:",
                -30, 130, this, springLayout,
                lblRequests);
        Configuration.applyLabelStyles(lblSortSection);

        btnRemove = UIComponentLibrary.createAButton("Remove",
                Configuration.defaultButtonWidthLarge, Configuration.defaultButtonHeight,
                Configuration.defaultButtonWidthLarge + 30, 0,
                actionListener,this, springLayout,
                btnRetrieve);
        Configuration.applyButtonStyles(btnRemove);

        btnReturn = UIComponentLibrary.createAButton("Return",
                Configuration.defaultButtonWidthLarge,Configuration.defaultButtonHeight,
                0, Configuration.defaultButtonHeight +20,
                actionListener,this, springLayout,
                btnRetrieve);
        Configuration.applyButtonStyles(btnReturn);

        btnAdd = UIComponentLibrary.createAButton("Add",
                Configuration.defaultButtonWidthLarge, Configuration.defaultButtonHeight,
                0,Configuration.defaultButtonHeight  + 20,
                actionListener,this, springLayout,
                btnRemove) ;
        Configuration.applyButtonStyles(btnAdd);


        txtSection = UIComponentLibrary.createATextField(5,
                73,0,this, springLayout, lblSortSection);
        Configuration.applyTextFieldStyles(txtSection);

        btnRandom = UIComponentLibrary.createAButton("Random Collection Sort",
                Configuration.defaultButtonWidthExtraLarge + 15, Configuration.defaultButtonHeight,
                Configuration.defaultButtonWidthLarge + 20, 0,
                actionListener,this, springLayout,
                lblSortSection);
        Configuration.applyButtonStyles(btnRandom);

        btnMostlySorted = UIComponentLibrary.createAButton("Mostly Sorted",
                Configuration.defaultButtonWidthExtraLarge, Configuration.defaultButtonHeight,
                0, Configuration.defaultButtonHeight + 10, actionListener,this, springLayout,
                lblSortSection);
        Configuration.applyButtonStyles(btnMostlySorted);

        btnReverseSort = UIComponentLibrary.createAButton("Reverse Sorted",
                Configuration.defaultButtonWidthExtraLarge, Configuration.defaultButtonHeight,
                Configuration.defaultButtonWidthExtraLarge + 5, 0,
                actionListener,this, springLayout, btnMostlySorted);
        Configuration.applyButtonStyles(btnReverseSort);
    }

}
