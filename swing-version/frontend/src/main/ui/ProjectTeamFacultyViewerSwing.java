package ui;

import dao.TeamDAO;
import model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProjectTeamFacultyViewerSwing extends JFrame implements ActionListener {
    private JTable teamTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private String facultyUsername;

    public ProjectTeamFacultyViewerSwing(String facultyUsername) {
        this.facultyUsername = facultyUsername;

        setTitle("CampusConnect - Student Project Team [Faculty Viewer]");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Student Project Teams Overview", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
                "ID", "Team Name", "Project Name", "Leader", "Members", "Subject",
                "Branch", "Year", "Section", "Academic Year", "Semester"
        }, 0);
        teamTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Project Releases"); // Updated label
        backButton.addActionListener(this);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTeams();
        setVisible(true);
    }

    private void loadTeams() {
        tableModel.setRowCount(0);
        List<Team> teams = new TeamDAO().getAllTeams();
        for (Team t : teams) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getTeamName(),
                    t.getProjectName() != null ? t.getProjectName() : "-",
                    t.getTeamLeaderUsername(),
                    t.getMembers(),
                    t.getSubjectName(),
                    t.getBranch(),
                    t.getYear(),
                    t.getSection(),
                    t.getAcademicYear(),
                    t.getSemester()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new ProjectReleaseSwing(facultyUsername); // Updated navigation
    }
}
