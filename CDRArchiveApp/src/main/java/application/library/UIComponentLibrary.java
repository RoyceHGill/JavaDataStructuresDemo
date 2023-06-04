package application.library;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UIComponentLibrary extends JFrame {
    public UIComponentLibrary() {
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, Frame frame, SpringLayout layout) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", frame);
        layout.putConstraint("North", myButton, posY, "North", frame);
        frame.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, Frame frame, SpringLayout layout, JTextField textArea) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", textArea);
        layout.putConstraint("North", myButton, posY, "North", textArea);
        frame.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", panel);
        layout.putConstraint("North", myButton, posY, "North", panel);
        panel.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout, JButton button) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", button);
        layout.putConstraint("North", myButton, posY, "North", button);
        panel.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout, JLabel label) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", label);
        layout.putConstraint("North", myButton, posY, "North", label);
        panel.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout, JTextField textField) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", textField);
        layout.putConstraint("North", myButton, posY, "North", textField);
        panel.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout, JTextArea textArea) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", textArea);
        layout.putConstraint("North", myButton, posY, "North", textArea);
        panel.add(myButton);
        return myButton;
    }

    public static JButton createAButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, JPanel panel, SpringLayout layout, ScrollPane scrollPane) {
        JButton myButton = new JButton(name);
        myButton.addActionListener(listener);
        myButton.setPreferredSize(new Dimension(sizeX, sizeY));
        layout.putConstraint("West", myButton, posX, "West", scrollPane);
        layout.putConstraint("North", myButton, posY, "North", scrollPane);
        panel.add(myButton);
        return myButton;
    }

    public static JTextField createATextField(int size, int posX, int posY, Frame frame, SpringLayout layout) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", frame);
        layout.putConstraint("North", myTextField, posY, "North", frame);
        frame.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, Frame frame, SpringLayout layout, JButton button) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", button);
        layout.putConstraint("North", myTextField, posY, "North", button);
        frame.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, Frame frame, SpringLayout layout, JLabel label) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", label);
        layout.putConstraint("North", myTextField, posY, "North", label);
        frame.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, JPanel panel, SpringLayout layout) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", panel);
        layout.putConstraint("North", myTextField, posY, "North", panel);
        panel.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, JPanel panel, SpringLayout layout, JButton button) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", button);
        layout.putConstraint("North", myTextField, posY, "North", button);
        panel.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, JPanel panel, SpringLayout layout, JLabel label) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", label);
        layout.putConstraint("North", myTextField, posY, "North", label);
        panel.add(myTextField);
        return myTextField;
    }

    public static JTextField createATextField(int size, int posX, int posY, JPanel panel, SpringLayout layout, JTextField textField) {
        JTextField myTextField = new JTextField(size);
        layout.putConstraint("West", myTextField, posX, "West", textField);
        layout.putConstraint("North", myTextField, posY, "North", textField);
        panel.add(myTextField);
        return myTextField;
    }


    public static JTextArea createATextArea(int cols, int rows, int posX, int posY, Frame frame, SpringLayout layout) {
        JTextArea myTextArea = new JTextArea(rows, cols);
        layout.putConstraint("West", myTextArea, posX, "West", frame);
        layout.putConstraint("North", myTextArea, posY, "North", frame);
        frame.add(myTextArea);
        return myTextArea;
    }
    public static JTextArea createATextArea(int cols, int rows, int posX, int posY, JPanel panel, SpringLayout layout) {
        JTextArea myTextArea = new JTextArea(rows, cols);
        layout.putConstraint("West", myTextArea, posX, "West", panel);
        layout.putConstraint("North", myTextArea, posY, "North", panel);
        panel.add(myTextArea);
        return myTextArea;
    }

    public static JTextArea createATextArea(int cols, int rows, int posX, int posY, JPanel panel, SpringLayout layout, JTextField textField) {
        JTextArea myTextArea = new JTextArea(rows, cols);
        layout.putConstraint("West", myTextArea, posX, "West", textField);
        layout.putConstraint("North", myTextArea, posY, "North", textField);
        panel.add(myTextArea);
        return myTextArea;
    }

    public static JTextArea createATextArea(int cols, int rows, int posX, int posY, JPanel panel, SpringLayout layout, JLabel label) {
        JTextArea myTextArea = new JTextArea(rows, cols);
        layout.putConstraint("West", myTextArea, posX, "West", label);
        layout.putConstraint("North", myTextArea, posY, "North", label);
        panel.add(myTextArea);
        return myTextArea;
    }
    public static JLabel createALabel(String text, int posX, int posY, Frame frame, SpringLayout layout) {
        JLabel myLabel = new JLabel(text);
        layout.putConstraint("West", myLabel, posX, "West", frame);
        layout.putConstraint("North", myLabel, posY, "North", frame);
        frame.add(myLabel);

        return myLabel;
    }

    public static JLabel createALabel(String text, int posX, int posY, Frame frame, SpringLayout layout, JTextField textField) {
        JLabel myLabel = new JLabel(text);
        layout.putConstraint("West", myLabel, posX, "West", textField);
        layout.putConstraint("North", myLabel, posY, "North", textField);
        frame.add(myLabel);
        return myLabel;

    }

    public static JLabel createALabel(String text, int posX, int posY, JPanel panel, SpringLayout layout) {
        JLabel myLabel = new JLabel(text);
        layout.putConstraint("West", myLabel, posX, "West", panel);
        layout.putConstraint("North", myLabel, posY, "North", panel);
        panel.add(myLabel);

        return myLabel;
    }

    public static JLabel createALabel(String text, int posX, int posY, JPanel panel, SpringLayout layout, JLabel label) {
        JLabel myLabel = new JLabel(text);
        layout.putConstraint("West", myLabel, posX, "West", label);
        layout.putConstraint("North", myLabel, posY, "North", label);
        panel.add(myLabel);

        return myLabel;
    }

}
