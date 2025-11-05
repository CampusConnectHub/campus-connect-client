package dao;

import model.Team;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    // Legacy method for basic team creation (used by ProjectTeamPanelSwing)
    public boolean createTeam(Team team) {
        String query = "INSERT INTO teams (team_name, members) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getMembers());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // New method for subject-based team creation with leader and metadata
    public boolean createTeamWithLeader(Team team) {
        String query = "INSERT INTO teams (team_name, members, subject_name, team_leader_username, branch, year, section, academic_year, semester) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getMembers());
            ps.setString(3, team.getSubjectName());
            ps.setString(4, team.getTeamLeaderUsername());
            ps.setString(5, team.getBranch());
            ps.setString(6, team.getYear());
            ps.setString(7, team.getSection());
            ps.setString(8, team.getAcademicYear());
            ps.setString(9, team.getSemester());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Team> getAllTeams() {
        List<Team> list = new ArrayList<>();
        String query = "SELECT * FROM teams";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Team team = new Team(
                        rs.getInt("id"),
                        rs.getString("team_name"),
                        rs.getString("members"),
                        rs.getString("subject_name"),
                        rs.getString("team_leader_username"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section"),
                        rs.getString("academic_year"),
                        rs.getString("semester")
                );
                list.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Team> getTeamsByStudent(String studentName) {
        List<Team> list = new ArrayList<>();
        String query = "SELECT * FROM teams WHERE members LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + studentName + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Team team = new Team(
                        rs.getInt("id"),
                        rs.getString("team_name"),
                        rs.getString("members"),
                        rs.getString("subject_name"),
                        rs.getString("team_leader_username"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section"),
                        rs.getString("academic_year"),
                        rs.getString("semester")
                );
                list.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Team> getTeamsBySubject(String subjectName) {
        List<Team> list = new ArrayList<>();
        String query = "SELECT * FROM teams WHERE subject_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Team team = new Team(
                        rs.getInt("id"),
                        rs.getString("team_name"),
                        rs.getString("members"),
                        rs.getString("subject_name"),
                        rs.getString("team_leader_username"),
                        rs.getString("branch"),
                        rs.getString("year"),
                        rs.getString("section"),
                        rs.getString("academic_year"),
                        rs.getString("semester")
                );
                list.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getEligibleStudents(String subjectName) {
        List<String> eligible = new ArrayList<>();
        String query = "SELECT username FROM users WHERE role = 'Student' AND username NOT IN " +
                "(SELECT team_leader_username FROM teams WHERE subject_name = ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                eligible.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eligible;
    }
}
