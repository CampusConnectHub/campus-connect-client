package ui;

import dao.EventDAO;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EventManagerSwing extends JFrame implements ActionListener {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton addButton, clearButton, backButton;
    private JList<String> eventList;
    private DefaultListModel<String> listModel;
    private JFrame caller;
    private String callerRole;

    public EventManagerSwing(JFrame caller, String role) {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Event Manager");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);

        addButton = new JButton("Add Event");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back to " + callerRole + " Dashboard");

        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        backButton.addActionListener(this);

        formPanel.add(new JLabel("Event Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descScroll);
        formPanel.add(addButton);
        formPanel.add(clearButton);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        loadEvents();
        setVisible(true);
    }

    private void loadEvents() {
        listModel.clear();
        List<Event> events = new EventDAO().getAllEvents();
        for (Event e : events) {
            listModel.addElement(e.getTitle() + " - " + e.getDescription());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == addButton) {
            String title = titleField.getText().trim();
            String desc = descriptionArea.getText().trim();

            if (!title.isEmpty()) {
                Event event = new Event(0, title, desc);
                boolean success = new EventDAO().addEvent(event);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Event added successfully!");
                    titleField.setText("");
                    descriptionArea.setText("");
                    loadEvents();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add event.");
                }
            }
        } else if (src == clearButton) {
            titleField.setText("");
            descriptionArea.setText("");
        } else if (src == backButton) {
            dispose();
            caller.setVisible(true);
        }
    }
}
