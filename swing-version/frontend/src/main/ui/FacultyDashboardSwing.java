package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FacultyDashboardSwing extends JFrame implements ActionListener {
    private JButton postNoticeButton, viewFeedbackButton, assignmentDashboardButton, attendanceButton;
    private JButton projectReleaseButton, submissionButton, notificationButton, logoutButton;
    private String username;

    public FacultyDashboardSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Faculty Dashboard");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height * 2 / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel headingLabel = new JLabel("CampusConnect Faculty Panel", SwingConstants.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        // Dashboard buttons
        postNoticeButton = createButton("Post Notice", "Publish announcements to students");
        viewFeedbackButton = createButton("View Feedback", "Review feedback from students");
        assignmentDashboardButton = createButton("Assignment Dashboard", "Manage assignments");
        attendanceButton = createButton("Attendance Panel", "Track student attendance");
        projectReleaseButton = createButton("Project Release", "Manage subject project workflows");
        submissionButton = createButton("View Submissions", "Review student submissions");
        notificationButton = createButton("Send Notification", "Compose or view notifications");
        logoutButton = createButton("Logout", "Exit the faculty dashboard");

        // Grid layout
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        gridPanel.add(postNoticeButton);
        gridPanel.add(viewFeedbackButton);
        gridPanel.add(assignmentDashboardButton);
        gridPanel.add(attendanceButton);
        gridPanel.add(projectReleaseButton);
        gridPanel.add(submissionButton);
        gridPanel.add(notificationButton);
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel(""));

        add(gridPanel, BorderLayout.CENTER);

        // Bottom panel
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

        if (src == postNoticeButton) {
            setVisible(false);
            new NoticeBoardSwing(this, "Faculty");
        } else if (src == viewFeedbackButton) {
            setVisible(false);
            new FeedbackPanelSwing(this, "Faculty", username);
        } else if (src == assignmentDashboardButton) {
            setVisible(false);
            new AssignmentDashboardSwing(username, "Faculty");
        } else if (src == attendanceButton) {
            setVisible(false);
            new AttendancePanelSwing(username, "FACULTY");
        } else if (src == projectReleaseButton) {
            setVisible(false);
            new ProjectReleaseSwing(username);
        } else if (src == submissionButton) {
            setVisible(false);
            new SubmissionReviewSwing(username, "Faculty");
        } else if (src == notificationButton) {
            // ✅ Open Notification Panel (hub)
            setVisible(false);
            new NotificationPanelSwing(username, "Faculty");
        } else if (src == logoutButton) {
            dispose();
            new LoginScreenSwing();
        }
    }
}
