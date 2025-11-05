package ui;

import dao.SubmissionDAO;
import model.Submission;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SubmissionReviewSwing extends JFrame implements ActionListener {
    private JButton viewAllButton, backButton;
    private JTable submissionTable;
    private DefaultTableModel tableModel;
    private String username, role;

    public SubmissionReviewSwing(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("CampusConnect - Review Submissions");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        viewAllButton = new JButton("View All Submissions");
        backButton = new JButton("Back to Dashboard");

        viewAllButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(viewAllButton);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Student", "Title", "Date", "Status"}, 0);
        submissionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(submissionTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadAllSubmissions() {
        tableModel.setRowCount(0);
        List<Submission> list = new SubmissionDAO().getAllSubmissions();
        for (Submission s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getStudentName(),
                    s.getAssignmentTitle(),
                    s.getSubmissionDate(),
                    s.getStatus()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewAllButton) {
            loadAllSubmissions();
        } else if (src == backButton) {
            dispose();
            switch (role) {
                case "Admin": new AdminDashboardSwing(username); break;
                case "Faculty": new FacultyDashboardSwing(username); break;
                default: JOptionPane.showMessageDialog(this, "Unknown role: " + role);
            }
        }
    }
}
