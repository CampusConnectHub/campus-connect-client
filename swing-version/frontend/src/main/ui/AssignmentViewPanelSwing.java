package ui;

import dao.AssignmentDAO;
import model.Assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AssignmentViewPanelSwing extends JFrame implements ActionListener {
    private JTable assignmentTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private String username;

    public AssignmentViewPanelSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - View Assignments");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Your Assignment Submissions", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
                "Title", "Description", "Due Date", "Status", "File Name", "Submitted At"
        }, 0);
        assignmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(assignmentTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadAssignments();
        setVisible(true);
    }

    private void loadAssignments() {
        tableModel.setRowCount(0);
        List<Assignment> list = new AssignmentDAO().getAssignmentsForStudent(username);
        for (Assignment a : list) {
            tableModel.addRow(new Object[]{
                    a.getTitle(),
                    a.getDescription(),
                    a.getDueDate(),
                    a.getSubmissionStatus(),
                    a.getSubmittedFileName(),
                    a.getSubmittedTimestamp()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new AssignmentSubmissionSwing(username);
    }
}
