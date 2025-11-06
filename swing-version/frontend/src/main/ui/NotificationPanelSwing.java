package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotificationPanelSwing extends JFrame implements ActionListener {
    private JButton composeButton, viewButton, emailAlertButton, backButton;
    private String username, role;

    public NotificationPanelSwing(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("CampusConnect - Notifications");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.5);
        int height = (int) (screenSize.height * 0.5);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Compose button (Admin/Faculty only)
        if (role.equals("Admin") || role.equals("Faculty")) {
            composeButton = new JButton("Compose New Notification");
            composeButton.addActionListener(this);
            panel.add(composeButton);
        }

        // View Notifications (visible to all)
        viewButton = new JButton("View Notifications");
        viewButton.addActionListener(this);
        panel.add(viewButton);

        // Email Alert stub
        emailAlertButton = new JButton("Send Email Notification Alerts");
        emailAlertButton.addActionListener(this);
        panel.add(emailAlertButton);

        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == composeButton) {
            new NotificationComposeSwing(username, role); // keep panel open
        } else if (src == viewButton) {
            // Pass this panel as parent so viewer can reopen it
            new NotificationViewerSwing(username, role, this);
            setVisible(false);
        } else if (src == emailAlertButton) {
            JOptionPane.showMessageDialog(this, "Feature not yet released!");
        } else if (src == backButton) {
            dispose();
            switch (role) {
                case "Admin": new AdminDashboardSwing(username); break;
                case "Faculty": new FacultyDashboardSwing(username); break;
                case "Student": new StudentDashboardSwing(username); break;
                default: JOptionPane.showMessageDialog(this, "Unknown role: " + role);
            }
        }
    }
}

