package ui;

import dao.AssignmentDAO;
import model.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AssignmentSubmitPanelSwing extends JFrame implements ActionListener {
    private JComboBox<String> assignmentDropdown;
    private JButton uploadButton, submitButton, backButton;
    private JLabel fileLabel;
    private File selectedFile;
    private String username;
    private List<Assignment> assignments;

    public AssignmentSubmitPanelSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Submit Assignment");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Submit Your Assignment", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        assignments = new AssignmentDAO().getAllAssignments();
        assignmentDropdown = new JComboBox<>();
        for (Assignment a : assignments) {
            assignmentDropdown.addItem(a.getTitle());
        }

        uploadButton = new JButton("Upload File");
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");
        fileLabel = new JLabel("No file selected");

        uploadButton.addActionListener(this);
        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centerPanel.add(new JLabel("Select Assignment:"));
        centerPanel.add(assignmentDropdown);
        centerPanel.add(fileLabel);
        centerPanel.add(uploadButton);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == uploadButton) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                fileLabel.setText("Selected: " + selectedFile.getName());
            }
        } else if (src == submitButton) {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(this, "Please upload a file first.");
                return;
            }

            int index = assignmentDropdown.getSelectedIndex();
            Assignment selected = assignments.get(index);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            boolean success = new AssignmentDAO().submitAssignment(
                    selected.getId(), username, selectedFile.getName(), timestamp
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Assignment submitted successfully.");
                dispose();
                new AssignmentSubmissionSwing(username);
            } else {
                JOptionPane.showMessageDialog(this, "Submission failed.");
            }
        } else if (src == backButton) {
            dispose();
            new AssignmentSubmissionSwing(username);
        }
    }
}
