package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EventManager extends Frame implements ActionListener {
    TextField titleField;
    TextArea descriptionArea;
    Button addButton, clearButton, backButton;
    List eventList;
    ArrayList<String> events;
    Frame caller;
    String callerRole;

    public EventManager(Frame caller, String role) {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Event Manager");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocation(300, 200);

        events = new ArrayList<>();

        // Form panel
        Panel formPanel = new Panel(new GridLayout(3, 2));
        formPanel.add(new Label("Event Title:"));
        titleField = new TextField();
        formPanel.add(titleField);

        formPanel.add(new Label("Description:"));
        descriptionArea = new TextArea(3, 20);
        formPanel.add(descriptionArea);

        addButton = new Button("Add Event");
        clearButton = new Button("Clear");
        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        formPanel.add(addButton);
        formPanel.add(clearButton);

        // Event list
        eventList = new List();

        // Back button
        backButton = new Button("Back to " + callerRole + " Dashboard");
        backButton.addActionListener(this);

        add(formPanel, BorderLayout.NORTH);
        add(eventList, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);

        // Handle ❌ close button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                caller.setVisible(true);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String title = titleField.getText().trim();
            String desc = descriptionArea.getText().trim();
            if (!title.isEmpty()) {
                String event = title + " - " + desc;
                events.add(event);
                eventList.add(event);
                titleField.setText("");
                descriptionArea.setText("");
            }
        } else if (e.getSource() == clearButton) {
            titleField.setText("");
            descriptionArea.setText("");
        } else if (e.getSource() == backButton) {
            dispose();
            caller.setVisible(true);
        }
    }
}