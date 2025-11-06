package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class NotificationDAO {

    // Insert a new notification
    public int createNotification(String title, String body, String priority,
                                  String senderUsername, String senderRole,
                                  String audienceType, String audiencePayload,
                                  boolean sendImmediately, Timestamp sendAt,
                                  Timestamp expiresAt) {
        String query = "INSERT INTO notifications " +
                "(title, body, priority, sender_username, sender_role, audience_type, audience_payload, " +
                "send_immediately, send_at, expires_at, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, title);
            ps.setString(2, body);
            ps.setString(3, priority);
            ps.setString(4, senderUsername);
            ps.setString(5, senderRole);
            ps.setString(6, audienceType);
            ps.setString(7, audiencePayload);
            ps.setBoolean(8, sendImmediately);
            ps.setTimestamp(9, sendAt);
            ps.setTimestamp(10, expiresAt);
            ps.setString(11, sendImmediately ? "SENT" : "SCHEDULED");

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // return notification_id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Fetch notifications (basic list)
    public List<Map<String, Object>> getNotifications(String role, String username) {
        List<Map<String, Object>> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE status='SENT' ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> notif = new HashMap<>();
                notif.put("id", rs.getInt("id"));
                notif.put("title", rs.getString("title"));
                notif.put("body", rs.getString("body"));
                notif.put("priority", rs.getString("priority"));
                notif.put("sender", rs.getString("sender_username"));
                notif.put("audience", rs.getString("audience_type") + " : " + rs.getString("audience_payload"));
                notif.put("status", rs.getString("status"));
                notif.put("created_at", rs.getTimestamp("created_at"));
                notif.put("send_at", rs.getTimestamp("send_at"));
                notif.put("expires_at", rs.getTimestamp("expires_at"));
                notifications.add(notif);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Delete notification
    public boolean deleteNotification(int notificationId) {
        String query = "DELETE FROM notifications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mark notification as read
    public boolean markAsRead(int notificationId, String username) {
        String query = "UPDATE notification_recipients SET read_at=CURRENT_TIMESTAMP " +
                "WHERE notification_id=? AND username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, notificationId);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
