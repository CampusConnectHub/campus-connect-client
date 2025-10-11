package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FacultyDashboard extends Frame implements ActionListener
{
    Button postNoticeButton, viewFeedbackButton, logoutButton;
    String username;

    public FacultyDashboard(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Faculty Dashboard");
        setSize(400, 250);
        setLayout(new GridLayout(3, 1));
        setLocation(350, 220);

        postNoticeButton = new Button("Post Notice");
        viewFeedbackButton = new Button("View Feedback");
        logoutButton = new Button("Logout");

        postNoticeButton.addActionListener(this);
        viewFeedbackButton.addActionListener(this);
        logoutButton.addActionListener(this);

        add(postNoticeButton);
        add(viewFeedbackButton);
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
        if (e.getSource() == postNoticeButton)
        {
            setVisible(false);
            new NoticeBoard(this, "Faculty");
        }
        else if (e.getSource() == viewFeedbackButton)
        {
            setVisible(false);
            new FeedbackPanel(this, "Faculty", username);
        }
        else if (e.getSource() == logoutButton)
        {
            dispose();
            new LoginScreen();
        }
    }
}