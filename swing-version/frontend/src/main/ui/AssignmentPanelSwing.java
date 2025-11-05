package ui;

import dao.AssignmentDAO;
import model.Assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AssignmentPanelSwing extends JFrame implements ActionListener {
    private JButton createAssignmentButton, deleteAssignmentButton, updateStatusButton, backButton;
    private JTable assignmentTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusBox;
    private String username, role;
    private AssignmentDashboardSwing parentDashboard;

    public AssignmentPanelSwing(String username, String role, AssignmentDashboardSwing parentDashboard) {
        this.username = username;
        this.role = role;
        this.parentDashboard = parentDashboard;

        setTitle("CampusConnect - Assignment Panel");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3; // Wider layout
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createAssignmentButton = new JButton("Create Assignment");
        deleteAssignmentButton = new JButton("Delete Selected");
        updateStatusButton = new JButton("Update Status");
        backButton = new JButton("Back to Dashboard");

        statusBox = new JComboBox<>(new String[]{"Submitted", "Not Submitted"});

        createAssignmentButton.addActionListener(this);
        deleteAssignmentButton.addActionListener(this);
        updateStatusButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(createAssignmentButton);
        buttonPanel.add(deleteAssignmentButton);
        buttonPanel.add(statusBox);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Due Date", "Submission Status"}, 0);
        assignmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(assignmentTable);

        add(scrollPane, BorderLayout.CENTER);

        loadAssignments();
        setVisible(true);
    }

    private void loadAssignments() {
        tableModel.setRowCount(0);
        List<Assignment> assignments = new AssignmentDAO().getAllAssignments();
        for (Assignment a : assignments) {
            tableModel.addRow(new Object[]{a.getId(), a.getTitle(), a.getDueDate(), a.getSubmissionStatus()});
        }
    }

    public void refreshTable() {
        loadAssignments();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createAssignmentButton) {
            new CreateAssignmentSwing(username, role, this);
        } else if (src == deleteAssignmentButton) {
            int selectedRow = assignmentTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                boolean success = new AssignmentDAO().deleteAssignmentById(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Assignment deleted successfully.");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete assignment.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an assignment to delete.");
            }
        } else if (src == updateStatusButton) {
            int selectedRow = assignmentTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String status = (String) statusBox.getSelectedItem();
                boolean success = new AssignmentDAO().updateSubmissionStatus(id, status);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Submission status updated.");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update status.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an assignment to update.");
            }
        } else if (src == backButton) {
            dispose();
            parentDashboard.setVisible(true);
        }
    }
}
