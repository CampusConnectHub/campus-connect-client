package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AttendancePanelSwing extends JFrame implements ActionListener, ItemListener {
    private JComboBox<String> academicYearBox, branchBox, classYearBox, semesterBox, sectionBox, roleBox;
    private JLabel nameLabel, userIdLabel, usernameLabel, dateLabel;
    private JButton chooseDateButton, continueButton, backButton;
    private String username, selectedDate, role;
    private boolean isHoliday;

    public AttendancePanelSwing(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("CampusConnect - Attendance Panel");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height * 2 / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        academicYearBox = new JComboBox<>(new String[]{"None", "2021-25", "2022-26", "2023-27", "2024-28"});
        branchBox = new JComboBox<>(new String[]{"None", "CSE (CORE)", "CSE (AI&ML)", "CSE (DS)", "CSE (CS)"});
        classYearBox = new JComboBox<>(new String[]{"None", "1st", "2nd", "3rd", "4th"});
        semesterBox = new JComboBox<>(new String[]{"None", "1", "2", "3", "4", "5", "6", "7", "8"});
        sectionBox = new JComboBox<>(getEnabledSectionsWithNone());

        academicYearBox.addItemListener(this);
        branchBox.addItemListener(this);
        classYearBox.addItemListener(this);
        semesterBox.addItemListener(this);
        sectionBox.addItemListener(this);

        UserDAO dao = new UserDAO();
        String displayName = dao.getNameByUsername(username, role);
        Integer userId = dao.getUserIdByUsername(username);

        nameLabel = new JLabel(displayName != null ? displayName : "Unknown");
        userIdLabel = new JLabel(userId != null ? String.valueOf(userId) : "");
        usernameLabel = new JLabel(username);

        roleBox = new JComboBox<>(new String[]{role});
        roleBox.setEnabled(false);

        dateLabel = new JLabel("No date selected");

        chooseDateButton = new JButton("Choose Attendance Date");
        continueButton = new JButton("Continue");
        backButton = new JButton("Back to Dashboard");

        chooseDateButton.setEnabled(false);
        continueButton.setEnabled(false);

        chooseDateButton.addActionListener(this);
        continueButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.add(new JLabel("User ID:"));
        formPanel.add(userIdLabel);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameLabel);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameLabel);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleBox);
        formPanel.add(new JLabel("Academic Year:"));
        formPanel.add(academicYearBox);
        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branchBox);
        formPanel.add(new JLabel("Class/Year:"));
        formPanel.add(classYearBox);
        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterBox);
        formPanel.add(new JLabel("Section:"));
        formPanel.add(sectionBox);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(dateLabel);
        datePanel.add(chooseDateButton);
        formPanel.add(new JLabel("Attendance Date:"));
        formPanel.add(datePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(backButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private String[] getEnabledSectionsWithNone() {
        return new String[]{"None", "A", "B", "C", "D", "E"};
    }

    private boolean allSelectionsMade() {
        return !academicYearBox.getSelectedItem().equals("None") &&
                !branchBox.getSelectedItem().equals("None") &&
                !classYearBox.getSelectedItem().equals("None") &&
                !semesterBox.getSelectedItem().equals("None") &&
                !sectionBox.getSelectedItem().equals("None");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            boolean enableButtons = allSelectionsMade();
            chooseDateButton.setEnabled(enableButtons);
            continueButton.setEnabled(enableButtons && selectedDate != null && !selectedDate.isEmpty());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == chooseDateButton) {
            if (!allSelectionsMade()) {
                JOptionPane.showMessageDialog(this, "Make required selections in all the checkboxes!", "Selection Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new AttendanceCalendarSwing(this, username, role);

        } else if (src == continueButton) {
            if (!allSelectionsMade()) {
                JOptionPane.showMessageDialog(this, "Make required selections in all the checkboxes!", "Selection Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (selectedDate == null || selectedDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a date first.");
                return;
            }

            String academicYear = (String) academicYearBox.getSelectedItem();
            String branch = (String) branchBox.getSelectedItem();
            String year = (String) classYearBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            String section = (String) sectionBox.getSelectedItem();

            if (isHoliday) {
                System.out.println("Holiday declared on: " + selectedDate);
            }

            dispose();
            new AttendanceStudentSwing(username, academicYear, branch, year, section, semester, selectedDate, role);

        } else if (src == backButton) {
            dispose();
            if (role.equalsIgnoreCase("ADMIN")) {
                new AdminDashboardSwing(username);
            } else {
                new FacultyDashboardSwing(username);
            }
        }
    }

    public void updateDate(String date, boolean isHoliday) {
        this.selectedDate = date;
        this.isHoliday = isHoliday;
        dateLabel.setText(date + (isHoliday ? " (Holiday)" : ""));
        chooseDateButton.setText("Change Attendance Date");

        continueButton.setEnabled(allSelectionsMade());
    }
}
