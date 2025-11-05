package ui;

import dao.GlobalConfigDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GlobalProjectConfigSwing extends JFrame implements ActionListener {
    private JComboBox<String> academicYearBox;
    private JList<String> sectionList;
    private JCheckBox disableProjectTeamsCheckbox;
    private JButton saveButton, backButton;
    private String adminUsername;

    public GlobalProjectConfigSwing(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("CampusConnect - Global Project Configuration");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Adjust Global Project Team Settings", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        academicYearBox = new JComboBox<>(new String[]{"2023-24", "2024-25", "2025-26"});
        sectionList = new JList<>(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});
        sectionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        sectionList.setVisibleRowCount(5);
        disableProjectTeamsCheckbox = new JCheckBox("Disable Project Team Feature Globally");

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.add(new JLabel("Set Global Academic Year:"));
        formPanel.add(academicYearBox);
        formPanel.add(new JLabel("Allowed Sections for Project Teams:"));
        formPanel.add(new JScrollPane(sectionList));
        formPanel.add(disableProjectTeamsCheckbox);

        add(formPanel, BorderLayout.CENTER);

        saveButton = new JButton("Save Configuration");
        backButton = new JButton("Back");
        saveButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadCurrentSettings();
        setVisible(true);
    }

    private void loadCurrentSettings() {
        GlobalConfigDAO dao = new GlobalConfigDAO();
        academicYearBox.setSelectedItem(dao.getGlobalAcademicYear());
        List<String> sections = dao.getAllowedSections();
        sectionList.clearSelection();
        for (int i = 0; i < sectionList.getModel().getSize(); i++) {
            if (sections.contains(sectionList.getModel().getElementAt(i))) {
                sectionList.addSelectionInterval(i, i);
            }
        }
        disableProjectTeamsCheckbox.setSelected(!dao.isProjectTeamFeatureEnabled());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == saveButton) {
            String academicYear = academicYearBox.getSelectedItem().toString();
            List<String> selectedSections = sectionList.getSelectedValuesList();
            boolean isEnabled = !disableProjectTeamsCheckbox.isSelected();

            boolean success = new GlobalConfigDAO().updateGlobalSettings(academicYear, selectedSections, isEnabled);
            JOptionPane.showMessageDialog(this, success ? "Settings updated!" : "Failed to update settings.");
        } else if (src == backButton) {
            dispose();
            new ProjectConfigAdminSwing(adminUsername);
        }
    }
}
