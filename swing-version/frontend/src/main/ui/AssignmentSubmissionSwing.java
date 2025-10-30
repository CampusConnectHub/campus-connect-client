package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignmentSubmissionSwing extends JFrame implements ActionListener
{
    private JButton viewAssignmentsButton, submitAssignmentButton, backButton;
    private String username;

    public AssignmentSubmissionSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Assignment Submission");
        setSize(400, 250);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewAssignmentsButton = new JButton("View Assignments");
        submitAssignmentButton = new JButton("Submit Assignment");
        backButton = new JButton("Back to Dashboard");

        viewAssignmentsButton.addActionListener(this);
        submitAssignmentButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(viewAssignmentsButton);
        panel.add(submitAssignmentButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewAssignmentsButton) {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View available assignments.");
        } else if (src == submitAssignmentButton) {
            JOptionPane.showMessageDialog(this, "Feature coming soon: Submit assignment.");
        } else if (src == backButton) {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}