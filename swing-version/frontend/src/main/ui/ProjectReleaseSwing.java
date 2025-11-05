package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectReleaseSwing extends JFrame implements ActionListener {
    private JButton setupButton, viewTeamsButton, viewSavedButton, backButton;
    private String facultyUsername;

    public ProjectReleaseSwing(String facultyUsername) {
        this.facultyUsername = facultyUsername;

        setTitle("CampusConnect - Project Release");
        setSize(600, 400); // Increased size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout

        JLabel heading = new JLabel("Manage Subject Project Workflows", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        setupButton = new JButton("Setup Subject Projects");
        viewTeamsButton = new JButton("View Student Project Teams");
        viewSavedButton = new JButton("View Saved Class Subjects");
        backButton = new JButton("Back to Dashboard");

        setupButton.setPreferredSize(new Dimension(200, 50));
        viewTeamsButton.setPreferredSize(new Dimension(200, 50));
        viewSavedButton.setPreferredSize(new Dimension(200, 50));
        backButton.setPreferredSize(new Dimension(200, 40));

        setupButton.addActionListener(this);
        viewTeamsButton.addActionListener(this);
        viewSavedButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel gridPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 10, 60));
        gridPanel.add(setupButton);
        gridPanel.add(viewTeamsButton);
        gridPanel.add(viewSavedButton);

        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == setupButton) {
            dispose();
            new ProjectSubjectSetupSwing(facultyUsername);
        } else if (src == viewTeamsButton) {
            dispose();
            new ProjectTeamFacultyViewerSwing(facultyUsername);
        } else if (src == viewSavedButton) {
            dispose();
            new ProjectSavedSubjectsSwing(facultyUsername);
        } else if (src == backButton) {
            dispose();
            new FacultyDashboardSwing(facultyUsername);
        }
    }
}
