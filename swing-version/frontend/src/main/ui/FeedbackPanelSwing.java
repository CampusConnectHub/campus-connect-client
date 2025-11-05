package ui;

import dao.FeedbackDAO;
import model.Feedback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FeedbackPanelSwing extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextArea feedbackArea;
    private JButton submitButton, clearButton, deleteButton, backButton;
    private JList<String> feedbackList;
    private DefaultListModel<String> listModel;
    private JFrame caller;
    private String callerRole;
    private String currentUser;
    private List<Feedback> feedbacks;

    public FeedbackPanelSwing(JFrame caller, String role, String username) {
        this.caller = caller;
        this.callerRole = role;
        this.currentUser = username;

        setTitle("CampusConnect - Feedback Panel");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        nameField = new JTextField(username);
        nameField.setEditable(false);
        feedbackArea = new JTextArea(3, 20);
        JScrollPane feedbackScroll = new JScrollPane(feedbackArea);

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        deleteButton = new JButton("Delete Selected");
        backButton = new JButton("Back to " + callerRole + " Dashboard");

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Your Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Feedback:"));
        formPanel.add(feedbackScroll);
        formPanel.add(submitButton);
        formPanel.add(clearButton);

        listModel = new DefaultListModel<>();
        feedbackList = new JList<>(listModel);
        feedbackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        if (callerRole.equals("Admin")) {
            feedbackArea.setEnabled(false);
            submitButton.setEnabled(false);
            clearButton.setEnabled(false);
        }

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(feedbackList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadFeedbacks();
        setVisible(true);
    }

    private void loadFeedbacks() {
        listModel.clear();
        if (callerRole.equals("Admin")) {
            feedbacks = new FeedbackDAO().getAllFeedbacks();
        } else {
            feedbacks = new FeedbackDAO().getFeedbacksByUser(currentUser);
        }

        for (Feedback f : feedbacks) {
            listModel.addElement(f.getUsername() + ": " + f.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == submitButton) {
            String message = feedbackArea.getText().trim();
            if (!message.isEmpty()) {
                Feedback feedback = new Feedback(0, currentUser, message);
                boolean success = new FeedbackDAO().submitFeedback(feedback);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Feedback submitted!");
                    feedbackArea.setText("");
                    loadFeedbacks();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit feedback.");
                }
            }
        } else if (src == clearButton) {
            feedbackArea.setText("");
        } else if (src == deleteButton) {
            int selectedIndex = feedbackList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Feedback selectedFeedback = feedbacks.get(selectedIndex);
                if (callerRole.equals("Admin") || selectedFeedback.getUsername().equals(currentUser)) {
                    boolean success = new FeedbackDAO().deleteFeedbackById(selectedFeedback.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Feedback deleted.");
                        loadFeedbacks();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete feedback.");
                    }
                }
            }
        } else if (src == backButton) {
            dispose();
            caller.setVisible(true);
        }
    }
}
