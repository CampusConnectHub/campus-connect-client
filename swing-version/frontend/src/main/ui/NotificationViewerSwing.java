package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NotificationViewerSwing extends JFrame {
    private JTable notificationTable;
    private DefaultTableModel tableModel;
    private String username, role;
    private JFrame parentDashboard; // reference to StudentDashboard

    public NotificationViewerSwing(String username, String role, JFrame parentDashboard) {
        this.username = username;
        this.role = role;
        this.parentDashboard = parentDashboard;

        setTitle("CampusConnect - View Notifications");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] columns = {
                "Title", "Sender", "Priority", "Audience", "Status", "Created", "Send At", "Expires"
        };
        tableModel = new DefaultTableModel(columns, 0);
        notificationTable = new JTable(tableModel);
        notificationTable.setRowHeight(30);
        notificationTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(notificationTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadNotifications());
        closeButton.addActionListener(e -> {
            dispose();
            if (parentDashboard != null) {
                parentDashboard.setVisible(true);
            }
        });

        // Also handle window close (X button)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (parentDashboard != null) {
                    parentDashboard.setVisible(true);
                }
            }
        });

        loadNotifications();
        setVisible(true);
    }

    private void loadNotifications() {
        tableModel.setRowCount(0);

        String query = "SELECT title, sender_username, priority, audience_type, audience_payload, " +
                "status, created_at, send_at, expires_at FROM notifications ORDER BY created_at DESC";

        try (Connection conn = util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String sender = rs.getString("sender_username");
                String priority = rs.getString("priority");
                String audience = rs.getString("audience_type") + ": " + rs.getString("audience_payload");
                String status = rs.getString("status");
                String created = rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toString() : "";
                String sendAt = rs.getTimestamp("send_at") != null ? rs.getTimestamp("send_at").toString() : "";
                String expires = rs.getTimestamp("expires_at") != null ? rs.getTimestamp("expires_at").toString() : "None";

                tableModel.addRow(new Object[]{
                        title, sender, priority, audience, status, created, sendAt, expires
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading notifications: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
