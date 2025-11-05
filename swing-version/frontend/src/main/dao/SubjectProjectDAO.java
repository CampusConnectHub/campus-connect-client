package dao;

import model.SubjectProjectConfig;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectProjectDAO {

    public boolean createConfig(SubjectProjectConfig config) {
        String query = "INSERT INTO subject_project_config (subject_name, faculty_username, academic_year, semester, branch, section, year, max_team_members, max_file_uploads, is_live) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, config.getSubjectName());
            ps.setString(2, config.getFacultyUsername());
            ps.setString(3, config.getAcademicYear());
            ps.setString(4, config.getSemester());
            ps.setString(5, config.getBranch());
            ps.setString(6, config.getSection());
            ps.setString(7, config.getYear());
            ps.setInt(8, config.getMaxTeamMembers());
            ps.setInt(9, config.getMaxFileUploads());
            ps.setBoolean(10, config.isLive());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateConfig(SubjectProjectConfig config) {
        String query = "UPDATE subject_project_config SET academic_year=?, semester=?, branch=?, section=?, year=?, max_team_members=?, max_file_uploads=?, is_live=? " +
                "WHERE subject_name=? AND faculty_username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, config.getAcademicYear());
            ps.setString(2, config.getSemester());
            ps.setString(3, config.getBranch());
            ps.setString(4, config.getSection());
            ps.setString(5, config.getYear());
            ps.setInt(6, config.getMaxTeamMembers());
            ps.setInt(7, config.getMaxFileUploads());
            ps.setBoolean(8, config.isLive());
            ps.setString(9, config.getSubjectName());
            ps.setString(10, config.getFacultyUsername());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getLiveSubjectsForStudent(String username) {
        List<String> subjects = new ArrayList<>();
        String query = "SELECT subject_name FROM subject_project_config WHERE is_live = true";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                subjects.add(rs.getString("subject_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public SubjectProjectConfig getConfigBySubject(String subjectName) {
        String query = "SELECT * FROM subject_project_config WHERE subject_name = ?";
        SubjectProjectConfig config = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                config = new SubjectProjectConfig(
                        rs.getString("subject_name"),
                        rs.getString("faculty_username"),
                        rs.getString("academic_year"),
                        rs.getString("semester"),
                        rs.getString("branch"),
                        rs.getString("section"),
                        rs.getString("year"),
                        rs.getInt("max_team_members"),
                        rs.getInt("max_file_uploads"),
                        rs.getBoolean("is_live")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }

    public List<SubjectProjectConfig> getConfigsByFaculty(String facultyUsername) {
        List<SubjectProjectConfig> list = new ArrayList<>();
        String query = "SELECT * FROM subject_project_config WHERE faculty_username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, facultyUsername);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubjectProjectConfig config = new SubjectProjectConfig(
                        rs.getString("subject_name"),
                        rs.getString("faculty_username"),
                        rs.getString("academic_year"),
                        rs.getString("semester"),
                        rs.getString("branch"),
                        rs.getString("section"),
                        rs.getString("year"),
                        rs.getInt("max_team_members"),
                        rs.getInt("max_file_uploads"),
                        rs.getBoolean("is_live")
                );
                list.add(config);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
