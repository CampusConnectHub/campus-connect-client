package dao;

import util.DBConnection;

import java.sql.*;
import java.util.*;

public class ClassStructureDAO {

    public boolean addBranch(String branch) {
        String query = "INSERT INTO class_structure (branch, academic_year) VALUES (?, NULL)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addAcademicYearWithYears(String branch, String academicYear) {
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String insertYearWithSection = "INSERT INTO class_structure (branch, academic_year, year, section) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertYearWithSection)) {
                for (String year : years) {
                    ps.setString(1, branch);
                    ps.setString(2, academicYear);
                    ps.setString(3, year);
                    ps.setString(4, "A"); //  Default section
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBranch(String branch) {
        String query = "DELETE FROM class_structure WHERE branch = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addAcademicYear(String branch, String academicYear) {
        String query = "INSERT INTO class_structure (branch, academic_year) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, academicYear);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSection(String branch, String academicYear, String year, String section) {
        String query = "INSERT INTO class_structure (branch, academic_year, year, section) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, academicYear);
            ps.setString(3, year);
            ps.setString(4, section);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAcademicYear(String branch, String academicYear) {
        String query = "DELETE FROM class_structure WHERE branch = ? AND academic_year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, academicYear);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSection(String branch, String academicYear, String year) {
        String query = "DELETE FROM class_structure WHERE branch = ? AND academic_year = ? AND year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ps.setString(2, academicYear);
            ps.setString(3, year);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Map<String, Map<String, List<String>>>> getClassTree() {
        Map<String, Map<String, Map<String, List<String>>>> tree = new TreeMap<>();

        String query = "SELECT branch, academic_year, year, section FROM class_structure";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String branch = rs.getString("branch");
                String academicYear = rs.getString("academic_year");
                String year = rs.getString("year");
                String section = rs.getString("section");

                if (academicYear == null) {
                    // Branch with no academic year
                    tree.computeIfAbsent(branch, b -> new TreeMap<>());
                    continue;
                }

                year = (year == null) ? "(No Year)" : year;
                section = (section == null) ? "(No Section)" : section;

                tree
                        .computeIfAbsent(branch, b -> new TreeMap<>())
                        .computeIfAbsent(academicYear, y -> new TreeMap<>())
                        .computeIfAbsent(year, s -> new ArrayList<>())
                        .add(section);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tree;
    }



    public List<String> getAllBranches() {
        List<String> branches = new ArrayList<>();
        String query = "SELECT DISTINCT branch FROM class_structure";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                branches.add(rs.getString("branch"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    public boolean branchExists(String branch) {
        String query = "SELECT COUNT(*) FROM class_structure WHERE branch = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, branch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
