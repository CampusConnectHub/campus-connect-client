package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboardSwing extends JFrame implements ActionListener
{
    private JButton viewNoticesButton, submitFeedbackButton, viewEventsButton, logoutButton;
    private String username;

    public StudentDashboardSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Student Dashboard");
        setSize(400, 300);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewNoticesButton = new JButton("View Notices");
        submitFeedbackButton = new JButton("Submit Feedback");
        viewEventsButton = new JButton("View Events");
        logoutButton = new JButton("Logout");

        viewNoticesButton.addActionListener(this);
        submitFeedbackButton.addActionListener(this);
        viewEventsButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(viewNoticesButton);
        panel.add(submitFeedbackButton);
        panel.add(viewEventsButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == viewNoticesButton)
        {
            setVisible(false);
            new NoticeBoardSwing(this, "Student");
        }
        else if (src == submitFeedbackButton)
        {
            setVisible(false);
            new FeedbackPanelSwing(this, "Student", username);
        }
        else if (src == viewEventsButton)
        {
            setVisible(false);
            new EventViewerSwing(this);
        }
        else if (src == logoutButton)
        {
            dispose();
            new LoginScreenSwing();
        }
    }
}