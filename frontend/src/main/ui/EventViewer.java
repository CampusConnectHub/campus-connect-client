package ui;

import java.awt.*;
import java.awt.event.*;

public class EventViewer extends Frame implements ActionListener {
    List eventList;
    Button backButton;
    Frame caller;

    public EventViewer(Frame caller) {
        this.caller = caller;

        setTitle("CampusConnect - Event Viewer");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocation(300, 200);

        eventList = new List();
        // Sample events — you can later load these dynamically
        eventList.add("Orientation - Welcome to Campus");
        eventList.add("Hackathon - Build & Win");
        eventList.add("Guest Lecture - AI in Industry");

        backButton = new Button("Back to Student Dashboard");
        backButton.addActionListener(this);

        add(eventList, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                caller.setVisible(true);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
        caller.setVisible(true);
    }
}