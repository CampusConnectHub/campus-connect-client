package dao;

import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean addFullUser(User user) {
        String query = "INSERT INTO users (username, role, name, roll_number, phone, email, branch, year, section, academic_year) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getName());
            ps.setString(4, user.getRollNumber());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getBranch());
            ps.setString(8, user.getYear());
            ps.setString(9, user.getSection());
            ps.setString(10, user.getAcademicYear());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, role) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRollNumber(rs.getString("roll_number"));
                user.setBranch(rs.getString("branch"));
                user.setYear(rs.getString("year"));
                user.setSection(rs.getString("section"));
                user.setAcademicYear(rs.getString("academic_year"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean registerUser(String email, String username, String password, String role, String name) {
        String query = "INSERT INTO users (email, username, password, role, name) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.setString(5, name);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEmailRegistered(String email) {
        String query = "SELECT id FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // true if email exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getNameByUsername(String username, String role) {
        String query = "SELECT name FROM users WHERE username = ? AND role = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<User> getUsersByClass(String branch, String year, String section, String academicYear) {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users WHERE branch = ? AND year = ? AND section = ? AND academic_year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, year);
            ps.setString(3, section);
            ps.setString(4, academicYear);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                user.setName(rs.getString("name"));
                user.setRollNumber(rs.getString("roll_number"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setBranch(rs.getString("branch"));
                user.setYear(rs.getString("year"));
                user.setSection(rs.getString("section"));
                user.setAcademicYear(rs.getString("academic_year"));
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                user.setName(rs.getString("name"));
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                user.setName(rs.getString("name"));
                user.setRollNumber(rs.getString("roll_number"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setBranch(rs.getString("branch"));
                user.setYear(rs.getString("year"));
                user.setSection(rs.getString("section"));
                user.setAcademicYear(rs.getString("academic_year"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getUserIdByUsername(String username) {
        String query = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET name = ?, phone = ?, email = ?, role = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserById(int id) {
        String query = "DELETE FROM users WHERE id = ?";

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
