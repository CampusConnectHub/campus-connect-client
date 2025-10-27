package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class NoticeBoard extends Frame implements ActionListener
{
    TextField noticeField;
    Button postButton, clearButton, backButton;
    List noticeList;
    ArrayList<String> notices;
    Frame caller;
    String callerRole;

    public NoticeBoard(Frame caller, String role)
    {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Notice Board");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocation(300, 200);

        notices = new ArrayList<>();

        // Top Panel: Posting Form
        Panel formPanel = new Panel(new GridLayout(2, 2));
        formPanel.add(new Label("Enter Notice:"));
        noticeField = new TextField();
        formPanel.add(noticeField);

        postButton = new Button("Post");
        clearButton = new Button("Clear");
        postButton.addActionListener(this);
        clearButton.addActionListener(this);
        formPanel.add(postButton);
        formPanel.add(clearButton);

        // Disable input for students
        if (callerRole.equals("Student"))
        {
            noticeField.setEnabled(false);
            postButton.setEnabled(false);
            clearButton.setEnabled(false);
        }

        // Center Panel: Notice List
        noticeList = new List();

        // Back button
        backButton = new Button("Back to " + callerRole + " Dashboard");
        backButton.addActionListener(this);

        add(formPanel, BorderLayout.NORTH);
        add(noticeList, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);

        // Handle ❌ close button
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
                caller.setVisible(true);
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == postButton)
        {
            String notice = noticeField.getText().trim();
            if (!notice.isEmpty())
            {
                notices.add(notice);
                noticeList.add(notice);
                noticeField.setText("");
            }
        }
        else if (e.getSource() == clearButton)
        {
            noticeField.setText("");
        }
        else if (e.getSource() == backButton)
        {
            dispose();
            caller.setVisible(true);
        }
    }
}