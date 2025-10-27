package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FeedbackPanelSwing extends JFrame implements ActionListener
{
    private JTextField nameField;
    private JTextArea feedbackArea;
    private JButton submitButton, clearButton, deleteButton, backButton;
    private JList<String> feedbackList;
    private DefaultListModel<String> listModel;
    private ArrayList<String> feedbacks;
    private JFrame caller;
    private String callerRole;
    private String currentUser;

    public FeedbackPanelSwing(JFrame caller, String role, String username)
    {
        this.caller = caller;
        this.callerRole = role;
        this.currentUser = username;

        setTitle("CampusConnect - Feedback Panel");
        setSize(500, 400);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        feedbacks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        feedbackList = new JList<>(listModel);
        feedbackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Your Name:"));
        nameField = new JTextField(username);
        nameField.setEditable(false);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Feedback:"));
        feedbackArea = new JTextArea(3, 20);
        JScrollPane feedbackScroll = new JScrollPane(feedbackArea);
        formPanel.add(feedbackScroll);

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        deleteButton = new JButton("Delete Selected");
        backButton = new JButton("Back to " + callerRole + " Dashboard");

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this);

        formPanel.add(submitButton);
        formPanel.add(clearButton);

        // Role-based access control
        if (callerRole.equals("Admin"))
        {
            feedbackArea.setEnabled(false);
            submitButton.setEnabled(false);
            clearButton.setEnabled(false);
        }

        loadFeedbacks();

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(feedbackList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadFeedbacks()
    {
        feedbacks.add("Rishit: Great platform!");
        feedbacks.add("Priya: Needs more events.");
        feedbacks.add("Rishit: UI is clean.");

        for (String entry : feedbacks)
        {
            if (callerRole.equals("Admin") || entry.startsWith(currentUser + ":"))
            {
                listModel.addElement(entry);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == submitButton)
        {
            String feedback = feedbackArea.getText().trim();
            if (!feedback.isEmpty())
            {
                String entry = currentUser + ": " + feedback;
                feedbacks.add(entry);
                listModel.addElement(entry);
                feedbackArea.setText("");
            }
        }
        else if (src == clearButton)
        {
            feedbackArea.setText("");
        }
        else if (src == deleteButton)
        {
            int selectedIndex = feedbackList.getSelectedIndex();
            if (selectedIndex >= 0)
            {
                String selectedEntry = listModel.getElementAt(selectedIndex);
                if (callerRole.equals("Admin") || selectedEntry.startsWith(currentUser + ":"))
                {
                    feedbacks.remove(selectedEntry);
                    listModel.remove(selectedIndex);
                }
            }
        }
        else if (src == backButton)
        {
            dispose();
            caller.setVisible(true);
        }
    }
}