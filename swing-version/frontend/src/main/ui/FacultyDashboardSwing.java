package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FacultyDashboardSwing extends JFrame implements ActionListener {
    private JButton postNoticeButton, viewFeedbackButton, reviewAssignmentsButton, attendanceButton, teamViewerButton, logoutButton;
    private String username;

    public FacultyDashboardSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Faculty Dashboard");
        setSize(400, 400);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        postNoticeButton = new JButton("Post Notice");
        viewFeedbackButton = new JButton("View Feedback");
        reviewAssignmentsButton = new JButton("Review Assignments");
        attendanceButton = new JButton("Attendance Panel");
        teamViewerButton = new JButton("View Project Teams");
        logoutButton = new JButton("Logout");

        postNoticeButton.addActionListener(this);
        viewFeedbackButton.addActionListener(this);
        reviewAssignmentsButton.addActionListener(this);
        attendanceButton.addActionListener(this);
        teamViewerButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(postNoticeButton);
        panel.add(viewFeedbackButton);
        panel.add(reviewAssignmentsButton);
        panel.add(attendanceButton);
        panel.add(teamViewerButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == postNoticeButton) {
            setVisible(false);
            new NoticeBoard(this, "Faculty");
        } else if (src == viewFeedbackButton) {
            setVisible(false);
            new FeedbackPanel(this, "Faculty", username);
        } else if (src == reviewAssignmentsButton) {
            setVisible(false);
            new AssignmentReviewSwing(username);
        } else if (src == attendanceButton) {
            setVisible(false);
            new AttendancePanelSwing(username);
        } else if (src == teamViewerButton) {
            setVisible(false);
            new ProjectTeamViewerSwing(username);
        } else if (src == logoutButton) {
            dispose();
            new LoginScreenSwing(); // Swing version of LoginScreen
        }
    }
}