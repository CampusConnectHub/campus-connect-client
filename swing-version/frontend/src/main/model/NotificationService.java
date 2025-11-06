package model;

import dao.NotificationDAO;
import dao.NotificationAttachmentDAO;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class NotificationService {
    private NotificationDAO notificationDAO;
    private NotificationAttachmentDAO attachmentDAO;

    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
        this.attachmentDAO = new NotificationAttachmentDAO();
    }

    /**
     * Publish a new notification
     */
    public int publishNotification(String title, String body, String priority,
                                   String senderUsername, String senderRole,
                                   String audienceType, String audiencePayload,
                                   boolean sendImmediately, Timestamp sendAt,
                                   Timestamp expiresAt,
                                   List<Map<String, Object>> attachments) {

        // 1. Insert notification
        int notificationId = notificationDAO.createNotification(
                title, body, priority,
                senderUsername, senderRole,
                audienceType, audiencePayload,
                sendImmediately, sendAt, expiresAt
        );

        if (notificationId == -1) {
            System.err.println("Failed to create notification");
            return -1;
        }

        // 2. Insert attachments if any
        if (attachments != null) {
            for (Map<String, Object> att : attachments) {
                String filename = (String) att.get("filename");
                String mimeType = (String) att.get("mime_type");
                long sizeBytes = (long) att.get("size_bytes");
                String storagePath = (String) att.get("storage_path");

                attachmentDAO.addAttachment(notificationId, filename, mimeType, sizeBytes, storagePath);
            }
        }

        // 3. Resolve audience → insert recipients
        List<String> recipients = resolveAudience(audienceType, audiencePayload);
        insertRecipients(notificationId, recipients);

        return notificationId;
    }

    /**
     * Resolve audience into a list of usernames using JDBC
     */
    private List<String> resolveAudience(String audienceType, String audiencePayload) {
        List<String> recipients = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps;
            ResultSet rs;

            switch (audienceType.toUpperCase()) {
                case "ALL":
                    ps = conn.prepareStatement("SELECT username FROM users");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        recipients.add(rs.getString("username"));
                    }
                    break;

                case "ROLE":
                    ps = conn.prepareStatement("SELECT username FROM users WHERE role = ?");
                    ps.setString(1, audiencePayload);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        recipients.add(rs.getString("username"));
                    }
                    break;

                case "BRANCH":
                    ps = conn.prepareStatement("SELECT username FROM class_structure WHERE branch = ?");
                    ps.setString(1, audiencePayload);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        recipients.add(rs.getString("username"));
                    }
                    break;

                case "YEAR":
                    ps = conn.prepareStatement("SELECT username FROM class_structure WHERE year = ?");
                    ps.setString(1, audiencePayload);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        recipients.add(rs.getString("username"));
                    }
                    break;

                case "SECTION":
                    ps = conn.prepareStatement("SELECT username FROM class_structure WHERE section = ?");
                    ps.setString(1, audiencePayload);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        recipients.add(rs.getString("username"));
                    }
                    break;

                case "USERS":
                    // Direct list of usernames (comma-separated)
                    recipients.addAll(Arrays.asList(audiencePayload.split(",")));
                    break;

                default:
                    System.err.println("Unknown audience type: " + audienceType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipients;
    }


    /**
     * Insert recipients into notification_recipients table
     */
    private void insertRecipients(int notificationId, List<String> recipients) {
        String query = "INSERT INTO notification_recipients (notification_id, username) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (String user : recipients) {
                ps.setInt(1, notificationId);
                ps.setString(2, user);
                ps.addBatch();
            }
            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch notifications for a user
     */
    public List<Map<String, Object>> getNotificationsForUser(String role, String username) {
        return notificationDAO.getNotifications(role, username);
    }

    /**
     * Mark notification as read
     */
    public boolean markAsRead(int notificationId, String username) {
        return notificationDAO.markAsRead(notificationId, username);
    }

    /**
     * Delete a notification
     */
    public boolean deleteNotification(int notificationId) {
        return notificationDAO.deleteNotification(notificationId);
    }
}
