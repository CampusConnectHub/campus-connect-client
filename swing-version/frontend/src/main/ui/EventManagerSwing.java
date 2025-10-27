package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EventManagerSwing extends JFrame implements ActionListener
{
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton addButton, clearButton, backButton;
    private JList<String> eventList;
    private DefaultListModel<String> listModel;
    private ArrayList<String> events;
    private JFrame caller;
    private String callerRole;

    public EventManagerSwing(JFrame caller, String role)
    {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Event Manager");
        setSize(500, 400);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        events = new ArrayList<>();
        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Event Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        formPanel.add(descScroll);

        addButton = new JButton("Add Event");
        clearButton = new JButton("Clear");
        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        formPanel.add(addButton);
        formPanel.add(clearButton);

        // Back button
        backButton = new JButton("Back to " + callerRole + " Dashboard");
        backButton.addActionListener(this);

        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == addButton)
        {
            String title = titleField.getText().trim();
            String desc = descriptionArea.getText().trim();
            if (!title.isEmpty())
            {
                String event = title + " - " + desc;
                events.add(event);
                listModel.addElement(event);
                titleField.setText("");
                descriptionArea.setText("");
            }
        }
        else if (src == clearButton)
        {
            titleField.setText("");
            descriptionArea.setText("");
        }
        else if (src == backButton)
        {
            dispose();
            caller.setVisible(true);
        }
    }
}