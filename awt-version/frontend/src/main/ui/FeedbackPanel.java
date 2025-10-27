package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FeedbackPanel extends Frame implements ActionListener
{
    TextField nameField;
    TextArea feedbackArea;
    Button submitButton, clearButton, deleteButton, backButton;
    List feedbackList;
    ArrayList<String> feedbacks;
    Frame caller;
    String callerRole;
    String currentUser;

    public FeedbackPanel(Frame caller, String role, String username)
    {
        this.caller = caller;
        this.callerRole = role;
        this.currentUser = username;

        setTitle("CampusConnect - Feedback Panel");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocation(300, 200);

        feedbacks = new ArrayList<>();

        // Form panel
        Panel formPanel = new Panel(new GridLayout(3, 2));
        formPanel.add(new Label("Your Name:"));
        nameField = new TextField(username); // Pre-fill name
        nameField.setEditable(false);
        formPanel.add(nameField);

        formPanel.add(new Label("Feedback:"));
        feedbackArea = new TextArea(3, 20);
        formPanel.add(feedbackArea);

        submitButton = new Button("Submit");
        clearButton = new Button("Clear");
        deleteButton = new Button("Delete Selected");
        backButton = new Button("Back to " + callerRole + " Dashboard");

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this);

        formPanel.add(submitButton);
        formPanel.add(clearButton);

        // Role-based access control
        if (callerRole.equals("Admin"))
        {
            feedbackArea.setEnabled(false);
            submitButton.setEnabled(false);
            clearButton.setEnabled(false);
        }

        // Feedback list
        feedbackList = new List();
        loadFeedbacks();

        // Bottom panel
        Panel bottomPanel = new Panel(new GridLayout(1, 2));
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        add(formPanel, BorderLayout.NORTH);
        add(feedbackList, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

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

    private void loadFeedbacks() {
        // Simulated feedbacks (replace with file/database later)
        feedbacks.add("Rishit: Great platform!");
        feedbacks.add("Priya: Needs more events.");
        feedbacks.add("Rishit: UI is clean.");

        for (String entry : feedbacks)
        {
            if (callerRole.equals("Admin"))
            {
                feedbackList.add(entry);
            }
            else
            {
                if (entry.startsWith(currentUser + ":"))
                {
                    feedbackList.add(entry);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
            String feedback = feedbackArea.getText().trim();
            if (!feedback.isEmpty())
            {
                String entry = currentUser + ": " + feedback;
                feedbacks.add(entry);
                feedbackList.add(entry);
                feedbackArea.setText("");
            }
        }
        else if (e.getSource() == clearButton)
        {
            feedbackArea.setText("");
        }
        else if (e.getSource() == deleteButton)
        {
            int selectedIndex = feedbackList.getSelectedIndex();
            if (selectedIndex >= 0)
            {
                String selectedEntry = feedbackList.getItem(selectedIndex);
                if (callerRole.equals("Admin") || selectedEntry.startsWith(currentUser + ":"))
                {
                    feedbacks.remove(selectedEntry);
                    feedbackList.remove(selectedIndex);
                }
            }
        }
        else if (e.getSource() == backButton)
        {
            dispose();
            caller.setVisible(true);
        }
    }
}