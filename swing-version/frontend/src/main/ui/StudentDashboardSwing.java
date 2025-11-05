package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboardSwing extends JFrame implements ActionListener {
    private JButton viewNoticesButton, submitFeedbackButton, viewEventsButton, assignmentButton;
    private JButton attendanceButton, releasedProjectsButton, submissionButton, logoutButton;
    private String username;

    public StudentDashboardSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Student Dashboard");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height * 2 / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel headingLabel = new JLabel("CampusConnect Student Panel", SwingConstants.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        viewNoticesButton = createButton("View Notices", "Check latest campus announcements");
        submitFeedbackButton = createButton("Submit Feedback", "Share your thoughts or issues");
        viewEventsButton = createButton("View Events", "Explore upcoming campus events");
        assignmentButton = createButton("Assignment Submission", "Submit your assignments");
        attendanceButton = createButton("View Attendance", "Track your attendance record");
        releasedProjectsButton = createButton("Released Subject Projects", "Access subject-based project options");
        submissionButton = createButton("My Submissions", "Review your past submissions");
        logoutButton = createButton("Logout", "Exit the student dashboard");

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        gridPanel.add(viewNoticesButton);
        gridPanel.add(submitFeedbackButton);
        gridPanel.add(viewEventsButton);
        gridPanel.add(assignmentButton);
        gridPanel.add(attendanceButton);
        gridPanel.add(releasedProjectsButton); // ✅ New button
        gridPanel.add(submissionButton);
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));

        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(180, 60));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewNoticesButton) {
            setVisible(false);
            new NoticeBoardSwing(this, "Student");
        } else if (src == submitFeedbackButton) {
            setVisible(false);
            new FeedbackPanelSwing(this, "Student", username);
        } else if (src == viewEventsButton) {
            setVisible(false);
            new EventViewerSwing(this);
        } else if (src == assignmentButton) {
            setVisible(false);
            new AssignmentSubmissionSwing(username);
        } else if (src == attendanceButton) {
            setVisible(false);
            new AttendanceViewerSwing(username);
        } else if (src == releasedProjectsButton) {
            setVisible(false);
            new ProjectReleaseStudentSwing(username); // Renamed class
        } else if (src == submissionButton) {
            setVisible(false);
            new SubmissionViewerSwing(username);
        } else if (src == logoutButton) {
            dispose();
            new LoginScreenSwing();
        }
    }
}
