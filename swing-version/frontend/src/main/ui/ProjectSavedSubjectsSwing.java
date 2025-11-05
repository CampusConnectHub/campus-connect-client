package ui;

import dao.SubjectProjectDAO;
import model.SubjectProjectConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProjectSavedSubjectsSwing extends JFrame implements ActionListener {
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private String facultyUsername;

    public ProjectSavedSubjectsSwing(String facultyUsername) {
        this.facultyUsername = facultyUsername;

        setTitle("CampusConnect - Saved Subject Projects");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Saved Subject Project Configurations", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
                "Subject", "Branch", "Year", "Section", "Semester", "Academic Year", "Max Members", "Max Uploads", "Live", "Edit"
        }, 0);
        subjectTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only "Edit" column is editable
            }
        };

        loadSubjects();

        subjectTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        subjectTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), facultyUsername));

        JScrollPane scrollPane = new JScrollPane(subjectTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadSubjects() {
        tableModel.setRowCount(0);
        List<SubjectProjectConfig> configs = new SubjectProjectDAO().getConfigsByFaculty(facultyUsername);
        for (SubjectProjectConfig config : configs) {
            tableModel.addRow(new Object[]{
                    config.getSubjectName(),
                    config.getBranch(),
                    config.getYear(),
                    config.getSection(),
                    config.getSemester(),
                    config.getAcademicYear(),
                    config.getMaxTeamMembers(),
                    config.getMaxFileUploads(),
                    config.isLive() ? "Yes" : "No",
                    "Edit"
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new ProjectReleaseSwing(facultyUsername);
    }
}
