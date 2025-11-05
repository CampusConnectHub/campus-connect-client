package dao;

import model.Submission;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {

    public boolean submitAssignment(Submission submission) {
        String query = "INSERT INTO submissions (student_name, assignment_title, submission_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, submission.getStudentName());
            ps.setString(2, submission.getAssignmentTitle());
            ps.setString(3, submission.getSubmissionDate());
            ps.setString(4, submission.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Submission> getSubmissionsByStudent(String studentName) {
        List<Submission> list = new ArrayList<>();
        String query = "SELECT * FROM submissions WHERE student_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Submission(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("assignment_title"),
                        rs.getString("submission_date"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Submission> getAllSubmissions() {
        List<Submission> list = new ArrayList<>();
        String query = "SELECT * FROM submissions";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Submission(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("assignment_title"),
                        rs.getString("submission_date"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
