package ui;

import dao.TeamDAO;
import model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProjectTeamViewerSwing extends JFrame implements ActionListener {
    private JButton viewTeamsButton, backButton;
    private JTable teamTable;
    private DefaultTableModel tableModel;
    private String username;

    public ProjectTeamViewerSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Team Viewer");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        viewTeamsButton = new JButton("View My Teams");
        backButton = new JButton("Back to Dashboard");

        viewTeamsButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(viewTeamsButton);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Team Name", "Members"}, 0);
        teamTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadStudentTeams() {
        tableModel.setRowCount(0);
        List<Team> list = new TeamDAO().getTeamsByStudent(username);
        for (Team t : list) {
            tableModel.addRow(new Object[]{t.getId(), t.getTeamName(), t.getMembers()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewTeamsButton) {
            loadStudentTeams();
        } else if (src == backButton) {
            dispose();
            new ProjectReleaseStudentSwing(username); //takes back to the Released Subject Project Section
        }
    }
}
