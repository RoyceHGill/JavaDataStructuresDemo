package application.services;

import javax.swing.*;
import java.awt.*;

public enum Configuration
{
    ;
    public static String sampleDataFileName = "..\\CD_ArchivePrototype_SampleData.txt";
    public static Dimension screenDimension = new Dimension(1060, 600);
    public static int defaultButtonHeight = 20;
    public static int defaultButtonWidth = 90;
    public static int defaultButtonWidthLarge = Configuration.defaultButtonWidth + 30;
    public static int defaultButtonWidthExtraLarge = Configuration.defaultButtonWidthLarge + 33;
    public static int buttonBorderThickness = 2;
    public static Color backgroundColour = new Color(50,50,50);
    public static Color panelBackgroundColour = new Color(80,80,80);
    public static Color primaryColour = new Color(110,110,110);
    public static Color secondaryColour = Color.gray;
    public static Color highlightColor = new Color(240,240,240);
    public static Font defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public static int dataEntryMargin = 20;
    public static int dataEntryLabelSpacing = 30;
    public static int dataEntryTextComponentMargin = 90;

    public static void applyButtonStyles(JButton btnNew) {
        btnNew.setForeground(highlightColor);
        btnNew.setBackground(primaryColour);
        btnNew.setBorder(BorderFactory.createLineBorder(secondaryColour, buttonBorderThickness, true));
    }

    public static void applyLabelStyles(JLabel label) {
        label.setFont(defaultFont);
        label.setForeground(highlightColor);
    }

    public static void applyTextFieldStyles(JTextField textField)
    {
        textField.setBorder(BorderFactory.createLineBorder(highlightColor, 2, false));
    }

    public static void applyTextFieldStyles(JTextArea textArea)
    {
        textArea.setBorder(BorderFactory.createLineBorder(highlightColor, 2, false));
    }

    public static void applyScrollPane(ScrollPane scrollPane)
    {

    }

}
