package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignmentPanelSwing extends JFrame implements ActionListener
{
    private JButton createAssignmentButton, backButton;
    private String username;

    public AssignmentPanelSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Assignment Panel");
        setSize(400, 200);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createAssignmentButton = new JButton("Create Assignment");
        backButton = new JButton("Back to Dashboard");

        createAssignmentButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(createAssignmentButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createAssignmentButton) {
            JOptionPane.showMessageDialog(this, "Feature coming soon: Create a new assignment.");
        } else if (src == backButton) {
            dispose();
            new FacultyDashboardSwing(username);
        }
    }
}