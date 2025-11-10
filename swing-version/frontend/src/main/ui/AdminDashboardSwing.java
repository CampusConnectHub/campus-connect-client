package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboardSwing extends JFrame implements ActionListener {
    private JButton eventButton, noticeButton, feedbackButton, notificationButton, userButton;
    private JButton assignmentButton, attendanceButton, projectConfigButton, logoutButton;
    private JButton adminManageButton; // NEW button for Admin Management
    private String username;

    public AdminDashboardSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Admin Dashboard");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height * 2 / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel headingLabel = new JLabel("CampusConnect Admin Panel", SwingConstants.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        // Dashboard buttons
        eventButton = createButton("Manage Events", "Create and manage campus events");
        noticeButton = createButton("Post Notices", "Publish announcements to students");
        feedbackButton = createButton("View Feedback", "Review feedback from users");
        notificationButton = createButton("Send Notification", "Compose or view notifications");
        userButton = createButton("Manage Users", "Add, edit, or remove users");
        assignmentButton = createButton("Assignment Dashboard", "Manage assignments");
        attendanceButton = createButton("Attendance Panel", "Track student attendance");
        projectConfigButton = createButton("Publish Subject Projects", "Create and manage subject project configurations");
        adminManageButton = createButton("Manage Admins", "Add, edit, or delete Admin accounts"); // NEW
        logoutButton = createButton("Logout", "Exit the admin dashboard");

        // Grid layout
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        gridPanel.add(eventButton);
        gridPanel.add(noticeButton);
        gridPanel.add(feedbackButton);
        gridPanel.add(notificationButton);
        gridPanel.add(userButton);
        gridPanel.add(assignmentButton);
        gridPanel.add(attendanceButton);
        gridPanel.add(projectConfigButton);
        gridPanel.add(adminManageButton); // NEW button added here

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

        if (src == eventButton) {
            setVisible(false);
            new EventManagerSwing(this, "Admin");
        } else if (src == noticeButton) {
            setVisible(false);
            new NoticeBoardSwing(this, "Admin");
        } else if (src == feedbackButton) {
            setVisible(false);
            new FeedbackPanelSwing(this, "Admin", username);
        } else if (src == notificationButton) {
            setVisible(false);
            new NotificationPanelSwing(username, "Admin");
        } else if (src == userButton) {
            setVisible(false);
            new UserManagementDirectorySwing(username);
        } else if (src == assignmentButton) {
            setVisible(false);
            new AssignmentDashboardSwing(username, "Admin");
        } else if (src == attendanceButton) {
            setVisible(false);
            new AttendancePanelSwing(username, "ADMIN");
        } else if (src == projectConfigButton) {
            setVisible(false);
            new ProjectConfigAdminSwing(username);
        } else if (src == adminManageButton) { // NEW action
            setVisible(false);
            new AccessAdminManagementSwing(this);
        } else if (src == logoutButton) {
            dispose();
            new LoginScreenSwing();
        }
    }
}
