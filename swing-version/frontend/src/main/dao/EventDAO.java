package dao;

import model.Event;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public boolean addEvent(Event event) {
        String query = "INSERT INTO events (title, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String query = "SELECT * FROM events";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
