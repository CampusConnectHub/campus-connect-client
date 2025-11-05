package ui;

import dao.SubjectProjectDAO;
import model.SubjectProjectConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectSubjectSetupSwing extends JFrame implements ActionListener {
    private JTextField subjectField;
    private JComboBox<String> yearBox, academicYearBox, branchBox, sectionBox, semesterBox;
    private JSpinner maxTeamSizeSpinner, maxFileUploadSpinner;
    private JCheckBox liveCheckbox;
    private JButton saveButton, backButton;
    private String facultyUsername;
    private SubjectProjectConfig existingConfig;

    public ProjectSubjectSetupSwing(String facultyUsername) {
        this.facultyUsername = facultyUsername;
        initUI();
    }

    // Overloaded constructor for editing
    public ProjectSubjectSetupSwing(String facultyUsername, SubjectProjectConfig config) {
        this.facultyUsername = facultyUsername;
        this.existingConfig = config;
        initUI();
        prefillFields(config);
    }

    private void initUI() {
        setTitle("CampusConnect - Subject Project Setup");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Configure Subject Project", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        subjectField = new JTextField();
        yearBox = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        academicYearBox = new JComboBox<>(new String[]{"2023-24", "2024-25", "2025-26"});
        branchBox = new JComboBox<>(new String[]{"CSE (CORE)", "CSE (AI&ML)", "CSE (CS)", "CSE (DS)"});
        sectionBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        semesterBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"});
        maxTeamSizeSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
        maxFileUploadSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        liveCheckbox = new JCheckBox("Make Project Live");

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.add(new JLabel("Subject Name:"));
        formPanel.add(subjectField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearBox);
        formPanel.add(new JLabel("Academic Year:"));
        formPanel.add(academicYearBox);
        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branchBox);
        formPanel.add(new JLabel("Section:"));
        formPanel.add(sectionBox);
        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterBox);
        formPanel.add(new JLabel("Max Team Members:"));
        formPanel.add(maxTeamSizeSpinner);
        formPanel.add(new JLabel("Max File Uploads:"));
        formPanel.add(maxFileUploadSpinner);
        formPanel.add(new JLabel(""));
        formPanel.add(liveCheckbox);

        add(formPanel, BorderLayout.CENTER);

        saveButton = new JButton(existingConfig == null ? "Save Configuration" : "Update Configuration");
        backButton = new JButton("Back");
        saveButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void prefillFields(SubjectProjectConfig config) {
        subjectField.setText(config.getSubjectName());
        academicYearBox.setSelectedItem(config.getAcademicYear());
        semesterBox.setSelectedItem(config.getSemester());
        branchBox.setSelectedItem(config.getBranch());
        sectionBox.setSelectedItem(config.getSection());
        yearBox.setSelectedItem(config.getYear());
        maxTeamSizeSpinner.setValue(config.getMaxTeamMembers());
        maxFileUploadSpinner.setValue(config.getMaxFileUploads());
        liveCheckbox.setSelected(config.isLive());
        subjectField.setEditable(false); // Prevent changing subject name during edit
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == saveButton) {
            SubjectProjectConfig config = new SubjectProjectConfig(
                    subjectField.getText().trim(),
                    facultyUsername,
                    academicYearBox.getSelectedItem().toString(),
                    semesterBox.getSelectedItem().toString(),
                    branchBox.getSelectedItem().toString(),
                    sectionBox.getSelectedItem().toString(),
                    yearBox.getSelectedItem().toString(),
                    (int) maxTeamSizeSpinner.getValue(),
                    (int) maxFileUploadSpinner.getValue(),
                    liveCheckbox.isSelected()
            );

            boolean success;
            if (existingConfig == null) {
                success = new SubjectProjectDAO().createConfig(config);
            } else {
                success = new SubjectProjectDAO().updateConfig(config);
            }

            JOptionPane.showMessageDialog(this, success ? "Configuration saved!" : "Failed to save.");
        } else if (src == backButton) {
            dispose();
            new ProjectReleaseSwing(facultyUsername);
        }
    }
}
