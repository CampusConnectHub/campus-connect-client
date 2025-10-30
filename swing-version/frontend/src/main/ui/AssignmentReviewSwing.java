package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignmentReviewSwing extends JFrame implements ActionListener
{
    private JButton viewSubmissionsButton, backButton;
    private String username;

    public AssignmentReviewSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Review Submissions");
        setSize(400, 200);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewSubmissionsButton = new JButton("View Submitted Assignments");
        backButton = new JButton("Back to Dashboard");

        viewSubmissionsButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(viewSubmissionsButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == viewSubmissionsButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View all student submissions.");
        }
        else if (src == backButton)
        {
            dispose();
            new FacultyDashboardSwing(username);
        }
    }
}