package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EventViewerSwing extends JFrame implements ActionListener
{
    private JList<String> eventList;
    private DefaultListModel<String> listModel;
    private JButton backButton;
    private JFrame caller;

    public EventViewerSwing(JFrame caller)
    {
        this.caller = caller;

        setTitle("CampusConnect - Event Viewer");
        setSize(500, 400);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Sample events — you can later load these dynamically
        listModel.addElement("Orientation - Welcome to Campus");
        listModel.addElement("Hackathon - Build & Win");
        listModel.addElement("Guest Lecture - AI in Industry");

        backButton = new JButton("Back to Student Dashboard");
        backButton.addActionListener(this);

        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        dispose();
        caller.setVisible(true);
    }
}