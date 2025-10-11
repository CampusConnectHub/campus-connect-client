package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class AdminDashboard extends Frame implements ActionListener
{
    Button eventButton, noticeButton, feedbackButton, logoutButton;
    String username;

    public AdminDashboard(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Admin Dashboard");
        setSize(500, 300);
        setLayout(new GridLayout(4, 1));
        setLocation(300, 200);

        eventButton = new Button("Manage Events");
        noticeButton = new Button("Post Notices");
        feedbackButton = new Button("View Feedback");
        logoutButton = new Button("Logout");

        eventButton.addActionListener(this);
        noticeButton.addActionListener(this);
        feedbackButton.addActionListener(this);
        logoutButton.addActionListener(this);

        add(eventButton);
        add(noticeButton);
        add(feedbackButton);
        add(logoutButton);

        setVisible(true);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == eventButton)
        {
            setVisible(false);
            new EventManager(this, "Admin");
        }
        else if (e.getSource() == noticeButton)
        {
            setVisible(false);
            new NoticeBoard(this, "Admin");
        }
        else if (e.getSource() == feedbackButton)
        {
            setVisible(false);
            new FeedbackPanel(this, "Admin", username);
        }
        else if (e.getSource() == logoutButton)
        {
            dispose();
            new LoginScreen();
        }
    }
}