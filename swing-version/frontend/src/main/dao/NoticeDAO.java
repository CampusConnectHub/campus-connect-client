package dao;

import model.Notice;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {

    public boolean postNotice(Notice notice) {
        String query = "INSERT INTO notices (content) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, notice.getContent());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notice> getAllNotices() {
        List<Notice> list = new ArrayList<>();
        String query = "SELECT * FROM notices ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Notice(
                        rs.getInt("id"),
                        rs.getString("content")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
