package dao;

import model.Assignment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new ArrayList<>();
        String query = "SELECT * FROM assignments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Assignment a = new Assignment(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("submission_status"),
                        rs.getString("submitted_file_name"),
                        rs.getString("submitted_timestamp"),
                        rs.getString("student_username")
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Assignment> getAssignmentsForStudent(String username) {
        List<Assignment> list = new ArrayList<>();
        String query = "SELECT * FROM assignments WHERE student_username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Assignment a = new Assignment(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("submission_status"),
                        rs.getString("submitted_file_name"),
                        rs.getString("submitted_timestamp"),
                        rs.getString("student_username")
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean createAssignment(Assignment assignment) {
        String query = "INSERT INTO assignments (title, description, due_date, submission_status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, assignment.getTitle());
            ps.setString(2, assignment.getDescription());
            ps.setString(3, assignment.getDueDate());
            ps.setString(4, assignment.getSubmissionStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAssignmentById(int id) {
        String query = "DELETE FROM assignments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAssignment(Assignment assignment) {
        String query = "UPDATE assignments SET title = ?, description = ?, due_date = ?, submission_status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, assignment.getTitle());
            ps.setString(2, assignment.getDescription());
            ps.setString(3, assignment.getDueDate());
            ps.setString(4, assignment.getSubmissionStatus());
            ps.setInt(5, assignment.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSubmissionStatus(int id, String status) {
        String query = "UPDATE assignments SET submission_status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean submitAssignment(int assignmentId, String username, String fileName, String timestamp) {
        String query = "UPDATE assignments SET submission_status = ?, submitted_file_name = ?, submitted_timestamp = ?, student_username = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "Submitted");
            ps.setString(2, fileName);
            ps.setString(3, timestamp);
            ps.setString(4, username);
            ps.setInt(5, assignmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
