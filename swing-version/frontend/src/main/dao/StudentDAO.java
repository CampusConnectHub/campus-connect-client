package dao;

import model.Student;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getEligibleStudents(String branch, String year, String section, String academicYear, String semester) {
        List<Student> list = new ArrayList<>();
        String query = "SELECT name, roll_number, username, branch, year, section, academic_year, semester " +
                "FROM users WHERE role='STUDENT' AND branch=? AND year=? AND section=? AND academic_year=? AND semester=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, year);
            ps.setString(3, section);
            ps.setString(4, academicYear);
            ps.setString(5, semester);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student(
                        rs.getString("name"),
                        rs.getString("roll_number"),
                        rs.getString("username"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section"),
                        rs.getString("academic_year"),
                        rs.getString("semester")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
