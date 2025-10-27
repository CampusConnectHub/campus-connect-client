package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FacultyDashboardSwing extends JFrame implements ActionListener
{
    private JButton postNoticeButton, viewFeedbackButton, logoutButton;
    private String username;

    public FacultyDashboardSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Faculty Dashboard");
        setSize(400, 250);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        postNoticeButton = new JButton("Post Notice");
        viewFeedbackButton = new JButton("View Feedback");
        logoutButton = new JButton("Logout");

        postNoticeButton.addActionListener(this);
        viewFeedbackButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(postNoticeButton);
        panel.add(viewFeedbackButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == postNoticeButton)
        {
            setVisible(false);
            new NoticeBoard(this, "Faculty");
        }
        else if (src == viewFeedbackButton)
        {
            setVisible(false);
            new FeedbackPanel(this, "Faculty", username);
        }
        else if (src == logoutButton)
        {
            dispose();
            new LoginScreenSwing(); // Swing version of LoginScreen
        }
    }
}