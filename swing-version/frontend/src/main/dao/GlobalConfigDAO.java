package dao;

import util.DBConnection;

import java.sql.*;
import java.util.*;

public class GlobalConfigDAO {

    public String getGlobalAcademicYear() {
        String query = "SELECT value FROM global_config WHERE key='project_academic_year'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("value");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "2024-25";
    }

    public List<String> getAllowedSections() {
        String query = "SELECT value FROM global_config WHERE key='project_sections'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Arrays.asList(rs.getString("value").split(","));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList("A", "B", "C", "D");
    }

    public boolean isProjectTeamFeatureEnabled() {
        String query = "SELECT value FROM global_config WHERE key='project_enabled'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("value").equalsIgnoreCase("true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateGlobalSettings(String academicYear, List<String> sections, boolean enabled) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            updateKey(conn, "project_academic_year", academicYear);
            updateKey(conn, "project_sections", String.join(",", sections));
            updateKey(conn, "project_enabled", String.valueOf(enabled));

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateKey(Connection conn, String key, String value) throws SQLException {
        String query = "UPDATE global_config SET value=? WHERE key=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, value);
            ps.setString(2, key);
            ps.executeUpdate();
        }
    }
}
