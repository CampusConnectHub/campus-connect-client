package ui;

import dao.StudentDAO;
import dao.SubjectProjectDAO;
import dao.TeamDAO;
import model.Student;
import model.SubjectProjectConfig;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectTeamStudentSubjectSwing extends JFrame implements ActionListener, ItemListener {
    private JComboBox<String> subjectDropdown;
    private JTextField teamNameField;
    private JComboBox<String> memberSelector;
    private DefaultComboBoxModel<String> memberModel;
    private JButton createTeamButton, backButton;
    private String username;
    private List<Student> eligibleStudents;

    public ProjectTeamStudentSubjectSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Subject Project Team");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Create Team for Live Subject", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        subjectDropdown = new JComboBox<>();
        subjectDropdown.addItem("Select Subject");
        List<String> liveSubjects = new SubjectProjectDAO().getLiveSubjectsForStudent(username);
        for (String subject : liveSubjects) {
            subjectDropdown.addItem(subject);
        }
        subjectDropdown.addItemListener(this);

        teamNameField = new JTextField();
        memberModel = new DefaultComboBoxModel<>();
        memberSelector = new JComboBox<>(memberModel);
        memberSelector.setEditable(true);
        memberSelector.setEnabled(false);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.add(new JLabel("Subject:"));
        formPanel.add(subjectDropdown);
        formPanel.add(new JLabel("Team Name:"));
        formPanel.add(teamNameField);
        formPanel.add(new JLabel("Select Member:"));
        formPanel.add(memberSelector);
        formPanel.add(new JLabel("You are the Team Leader"));
        formPanel.add(new JLabel(username));

        add(formPanel, BorderLayout.CENTER);

        createTeamButton = new JButton("Create Team");
        backButton = new JButton("Back");
        createTeamButton.setEnabled(false);
        createTeamButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createTeamButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == subjectDropdown && e.getStateChange() == ItemEvent.SELECTED) {
            String selectedSubject = subjectDropdown.getSelectedItem().toString();
            if (!selectedSubject.equals("Select Subject")) {
                SubjectProjectConfig config = new SubjectProjectDAO().getConfigBySubject(selectedSubject);
                eligibleStudents = new StudentDAO().getEligibleStudents(
                        config.getBranch(),
                        config.getYear(),
                        config.getSection(),
                        config.getAcademicYear(),
                        config.getSemester()
                );

                memberModel.removeAllElements();
                for (Student s : eligibleStudents) {
                    memberModel.addElement(s.toString()); // shows name + roll
                }

                memberSelector.setEnabled(true);
                createTeamButton.setEnabled(true);
            } else {
                memberModel.removeAllElements();
                memberSelector.setEnabled(false);
                createTeamButton.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createTeamButton) {
            String subject = subjectDropdown.getSelectedItem().toString();
            SubjectProjectConfig config = new SubjectProjectDAO().getConfigBySubject(subject);

            String selectedMemberDisplay = memberSelector.getSelectedItem().toString();
            String selectedUsername = eligibleStudents.stream()
                    .filter(s -> s.toString().equals(selectedMemberDisplay))
                    .map(Student::getUsername)
                    .findFirst()
                    .orElse(null);

            if (selectedUsername == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid member from the list.");
                return;
            }

            Team team = new Team(0,
                    teamNameField.getText().trim(),
                    selectedUsername,
                    subject,
                    username,
                    config.getBranch(),
                    config.getYear(),
                    config.getSection(),
                    config.getAcademicYear(),
                    config.getSemester()
            );

            boolean success = new TeamDAO().createTeamWithLeader(team);
            JOptionPane.showMessageDialog(this, success ? "Team created!" : "Failed to create team.");
        } else if (src == backButton) {
            dispose();
            new ProjectReleaseStudentSwing(username);
        }
    }
}
