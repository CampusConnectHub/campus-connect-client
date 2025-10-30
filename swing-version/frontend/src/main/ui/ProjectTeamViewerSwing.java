package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectTeamViewerSwing extends JFrame implements ActionListener {
    private JButton viewTeamsButton, backButton;
    private String username;

    public ProjectTeamViewerSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Team Viewer");
        setSize(400, 200);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewTeamsButton = new JButton("View All Teams");
        backButton = new JButton("Back to Dashboard");

        viewTeamsButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(viewTeamsButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewTeamsButton) {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View all project teams.");
        } else if (src == backButton) {
            dispose();
            new FacultyDashboardSwing(username);
        }
    }
}