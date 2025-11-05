package dao;

import model.Attendance;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT * FROM attendance";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("date"),
                        rs.getBoolean("present"),
                        rs.getString("academic_year"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section")
                );
                a.setTimestamp(rs.getString("timestamp"));
                a.setMarkedBy(rs.getString("marked_by"));
                a.setSemester(rs.getString("semester"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Attendance> getAttendanceByStudent(String studentName) {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, studentName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("date"),
                        rs.getBoolean("present"),
                        rs.getString("academic_year"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section")
                );
                a.setTimestamp(rs.getString("timestamp"));
                a.setMarkedBy(rs.getString("marked_by"));
                a.setSemester(rs.getString("semester"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Attendance> getAttendanceByDateAndSection(String date, String branch, String year, String section, String semester) {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE date = ? AND branch = ? AND year = ? AND section = ? AND semester = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, date);
            ps.setString(2, branch);
            ps.setString(3, year);
            ps.setString(4, section);
            ps.setString(5, semester);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("date"),
                        rs.getBoolean("present"),
                        rs.getString("academic_year"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section")
                );
                a.setTimestamp(rs.getString("timestamp"));
                a.setMarkedBy(rs.getString("marked_by"));
                a.setSemester(rs.getString("semester"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean markAttendance(Attendance attendance) {
        String query = "INSERT INTO attendance (student_name, date, present, academic_year, branch, year, section, semester, timestamp, marked_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, attendance.getStudentName());
            ps.setString(2, attendance.getDate());
            ps.setBoolean(3, attendance.isPresent());
            ps.setString(4, attendance.getAcademicYear());
            ps.setString(5, attendance.getBranch());
            ps.setString(6, attendance.getYear());
            ps.setString(7, attendance.getSection());
            ps.setString(8, attendance.getSemester());
            ps.setString(9, attendance.getTimestamp());
            ps.setString(10, attendance.getMarkedBy());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAttendance(Attendance attendance) {
        String query = "UPDATE attendance SET present = ?, timestamp = ?, marked_by = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setBoolean(1, attendance.isPresent());
            ps.setString(2, attendance.getTimestamp());
            ps.setString(3, attendance.getMarkedBy());
            ps.setInt(4, attendance.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAttendance(int id) {
        String query = "DELETE FROM attendance WHERE id = ?";

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
