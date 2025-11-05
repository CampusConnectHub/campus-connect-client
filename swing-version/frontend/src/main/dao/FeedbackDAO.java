package dao;

import model.Feedback;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public boolean submitFeedback(Feedback feedback) {
        String query = "INSERT INTO feedback (username, message) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, feedback.getUsername());
            ps.setString(2, feedback.getMessage());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getFeedbacksByUser(String username) {
        List<Feedback> list = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("message")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> list = new ArrayList<>();
        String query = "SELECT * FROM feedback";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("message")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteFeedbackById(int id) {
        String query = "DELETE FROM feedback WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
