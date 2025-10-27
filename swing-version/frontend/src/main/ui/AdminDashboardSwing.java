package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboardSwing extends JFrame implements ActionListener
{
    private JButton eventButton, noticeButton, feedbackButton, logoutButton;
    private String username;

    public AdminDashboardSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Admin Dashboard");
        setSize(500, 300);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create buttons
        eventButton = new JButton("Manage Events");
        noticeButton = new JButton("Post Notices");
        feedbackButton = new JButton("View Feedback");
        logoutButton = new JButton("Logout");

        // Add action listeners
        eventButton.addActionListener(this);
        noticeButton.addActionListener(this);
        feedbackButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Layout setup
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(eventButton);
        panel.add(noticeButton);
        panel.add(feedbackButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == eventButton)
        {
            setVisible(false);
            new EventManager(this, "Admin");
        }
        else if (src == noticeButton)
        {
            setVisible(false);
            new NoticeBoard(this, "Admin");
        }
        else if (src == feedbackButton)
        {
            setVisible(false);
            new FeedbackPanel(this, "Admin", username);
        }
        else if (src == logoutButton)
        {
            dispose();
            new LoginScreenSwing(); // Swing version of LoginScreen
        }
    }
}