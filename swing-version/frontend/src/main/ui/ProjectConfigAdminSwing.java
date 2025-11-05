package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectConfigAdminSwing extends JFrame implements ActionListener {
    private JButton createConfigButton, viewConfigsButton, globalConfigButton, backButton;
    private String adminUsername;

    public ProjectConfigAdminSwing(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("CampusConnect - Subject Project Configuration");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Manage Subject Project Configurations", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        createConfigButton = new JButton("Create Subject Project");
        viewConfigsButton = new JButton("View All Configurations");
        globalConfigButton = new JButton("Adjust Global Configuration");
        backButton = new JButton("Back to Admin Dashboard");

        createConfigButton.setPreferredSize(new Dimension(200, 50));
        viewConfigsButton.setPreferredSize(new Dimension(200, 50));
        globalConfigButton.setPreferredSize(new Dimension(200, 50));
        backButton.setPreferredSize(new Dimension(200, 40));

        createConfigButton.addActionListener(this);
        viewConfigsButton.addActionListener(this);
        globalConfigButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel gridPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 10, 60));
        gridPanel.add(createConfigButton);
        gridPanel.add(viewConfigsButton);
        gridPanel.add(globalConfigButton);

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

        if (src == createConfigButton) {
            dispose();
            new ProjectSubjectSetupSwing(adminUsername); // Reuse setup screen
        } else if (src == viewConfigsButton) {
            dispose();
            new ProjectSavedSubjectsSwing(adminUsername); // Reuse viewer
        } else if (src == globalConfigButton) {
            dispose();
            new GlobalProjectConfigSwing(adminUsername); // ✅ New screen
        } else if (src == backButton) {
            dispose();
            new AdminDashboardSwing(adminUsername);
        }
    }
}
