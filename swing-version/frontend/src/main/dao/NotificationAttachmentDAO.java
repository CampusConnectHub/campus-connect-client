package dao;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class NotificationAttachmentDAO {

    // Insert a new attachment for a notification
    public boolean addAttachment(int notificationId, String filename,
                                 String mimeType, long sizeBytes, String storagePath) {
        String query = "INSERT INTO notification_attachments " +
                "(notification_id, filename, mime_type, size_bytes, storage_path, uploaded_at) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, notificationId);
            ps.setString(2, filename);
            ps.setString(3, mimeType);
            ps.setLong(4, sizeBytes);
            ps.setString(5, storagePath);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch all attachments for a notification
    public List<Map<String, Object>> getAttachments(int notificationId) {
        List<Map<String, Object>> attachments = new ArrayList<>();
        String query = "SELECT * FROM notification_attachments WHERE notification_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, notificationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> att = new HashMap<>();
                att.put("id", rs.getInt("id"));
                att.put("filename", rs.getString("filename"));
                att.put("mime_type", rs.getString("mime_type"));
                att.put("size_bytes", rs.getLong("size_bytes"));
                att.put("storage_path", rs.getString("storage_path"));
                att.put("uploaded_at", rs.getTimestamp("uploaded_at"));
                attachments.add(att);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachments;
    }

    // Delete attachment
    public boolean deleteAttachment(int attachmentId) {
        String query = "DELETE FROM notification_attachments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, attachmentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
