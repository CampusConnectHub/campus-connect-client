package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AccessAdminManagementSwing extends JFrame {
    private JTable adminTable;
    private DefaultTableModel tableModel;
    private JButton addAdminButton, backButton;
    private AdminDashboardSwing dashboardRef;

    public AccessAdminManagementSwing(AdminDashboardSwing dashboardRef) {
        this.dashboardRef = dashboardRef;

        setTitle("Manage Admin Accounts");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Admin Management", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        String[] columns = {"Username", "Email", "Edit", "Delete"};
        tableModel = new DefaultTableModel(columns, 0);
        adminTable = new JTable(tableModel);
        adminTable.setRowHeight(30);
        adminTable.setDefaultEditor(Object.class, null);

        adminTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = adminTable.rowAtPoint(e.getPoint());
                int col = adminTable.columnAtPoint(e.getPoint());
                if (row < 0 || col < 0) return;

                String username = adminTable.getValueAt(row, 0).toString();
                Integer userId = new UserDAO().getUserIdByUsername(username);
                if (userId == null) {
                    JOptionPane.showMessageDialog(AccessAdminManagementSwing.this, "Admin not found.");
                    return;
                }

                if (col == 2) {
                    new AccessAdminEditPopupSwing(userId, AccessAdminManagementSwing.this::loadAdmins).setVisible(true);
                } else if (col == 3) {
                    new UserManagementDeleteConfirmPopupSwing(
                            "Delete admin '" + username + "'?",
                            () -> {
                                boolean success = new UserDAO().deleteUserById(userId);
                                JOptionPane.showMessageDialog(AccessAdminManagementSwing.this,
                                        success ? "Admin deleted." : "Failed to delete.");
                                if (success) loadAdmins();
                            }
                    );
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(adminTable);
        add(scrollPane, BorderLayout.CENTER);

        addAdminButton = new JButton("Add Admin");
        addAdminButton.addActionListener(e -> new AccessAdminAddPopupSwing(this, this::loadAdmins).setVisible(true));

        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            dispose();
            if (dashboardRef != null) dashboardRef.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addAdminButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadAdmins();
        setVisible(true);
    }

    private void loadAdmins() {
        tableModel.setRowCount(0);
        List<User> admins = new UserDAO().getUsersByRole("ADMIN");
        for (User u : admins) {
            tableModel.addRow(new Object[]{u.getUsername(), u.getEmail(), "Edit", "Delete"});
        }
    }
}
