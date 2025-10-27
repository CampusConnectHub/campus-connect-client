package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StudentDashboard extends Frame implements ActionListener
{
    Button viewNoticesButton, submitFeedbackButton, viewEventsButton, logoutButton;
    String username;

    public StudentDashboard(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Student Dashboard");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1));
        setLocation(350, 220);

        viewNoticesButton = new Button("View Notices");
        submitFeedbackButton = new Button("Submit Feedback");
        viewEventsButton = new Button("View Events");
        logoutButton = new Button("Logout");

        viewNoticesButton.addActionListener(this);
        submitFeedbackButton.addActionListener(this);
        viewEventsButton.addActionListener(this);
        logoutButton.addActionListener(this);

        add(viewNoticesButton);
        add(submitFeedbackButton);
        add(viewEventsButton);
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
        if (e.getSource() == viewNoticesButton)
        {
            setVisible(false);
            new NoticeBoard(this, "Student");
        }
        else if (e.getSource() == submitFeedbackButton)
        {
            setVisible(false);
            new FeedbackPanel(this, "Student", username);
        }
        else if (e.getSource() == viewEventsButton)
        {
            setVisible(false);
            new EventViewer(this);
        }
        else if (e.getSource() == logoutButton)
        {
            dispose();
            new LoginScreen();
        }
    }
}