package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotificationPanelSwing extends JFrame implements ActionListener {
    private JButton createNotificationButton, backButton;
    private String username;

    public NotificationPanelSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Notifications");
        setSize(400, 200);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createNotificationButton = new JButton("Create Notification");
        backButton = new JButton("Back to Dashboard");

        createNotificationButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(createNotificationButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createNotificationButton) {
            JOptionPane.showMessageDialog(this, "Feature coming soon: Compose and send notifications.");
        } else if (src == backButton) {
            dispose();
            new AdminDashboardSwing(username);
        }
    }
}