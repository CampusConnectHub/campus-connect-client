package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectReleaseStudentSwing extends JFrame implements ActionListener {
    private JButton subjectTeamButton, myTeamButton, backButton;
    private String username;

    public ProjectReleaseStudentSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Released Subject Projects");
        setSize(600, 400); // Spacious layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Subject Project Team Options", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        subjectTeamButton = new JButton("Subject Project Teams");
        myTeamButton = new JButton("My Project Teams");
        backButton = new JButton("Back to Student Dashboard");

        subjectTeamButton.setPreferredSize(new Dimension(200, 50));
        myTeamButton.setPreferredSize(new Dimension(200, 50));
        backButton.setPreferredSize(new Dimension(200, 40));

        subjectTeamButton.addActionListener(this);
        myTeamButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel gridPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 10, 60));
        gridPanel.add(subjectTeamButton);
        gridPanel.add(myTeamButton);

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

        if (src == subjectTeamButton) {
            dispose();
            new ProjectTeamStudentSubjectSwing(username);
        } else if (src == myTeamButton) {
            dispose();
            new ProjectTeamViewerSwing(username);
        } else if (src == backButton) {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}
