package ui;

import dao.SubmissionDAO;
import model.Submission;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SubmissionPanelSwing extends JFrame implements ActionListener {
    private JTextField titleField, dateField;
    private JComboBox<String> statusBox;
    private JButton submitButton, backButton;
    private String username;

    public SubmissionPanelSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Submit Assignment");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        titleField = new JTextField();
        dateField = new JTextField();
        statusBox = new JComboBox<>(new String[]{"Submitted", "Pending"});
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        add(new JLabel("Assignment Title:"));
        add(titleField);
        add(new JLabel("Submission Date (YYYY-MM-DD):"));
        add(dateField);
        add(statusBox);
        add(submitButton);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String title = titleField.getText().trim();
            String date = dateField.getText().trim();
            String status = (String) statusBox.getSelectedItem();

            Submission submission = new Submission(0, username, title, date, status);
            boolean success = new SubmissionDAO().submitAssignment(submission);

            if (success) {
                JOptionPane.showMessageDialog(this, "Assignment submitted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Submission failed.");
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}
