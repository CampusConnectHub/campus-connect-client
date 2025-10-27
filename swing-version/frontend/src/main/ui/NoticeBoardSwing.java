package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NoticeBoardSwing extends JFrame implements ActionListener
{
    private JTextField noticeField;
    private JButton postButton, clearButton, backButton;
    private JList<String> noticeList;
    private DefaultListModel<String> listModel;
    private ArrayList<String> notices;
    private JFrame caller;
    private String callerRole;

    public NoticeBoardSwing(JFrame caller, String role)
    {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Notice Board");
        setSize(500, 400);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        notices = new ArrayList<>();
        listModel = new DefaultListModel<>();
        noticeList = new JList<>(listModel);
        noticeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Top Panel: Posting Form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Enter Notice:"));
        noticeField = new JTextField();
        formPanel.add(noticeField);

        postButton = new JButton("Post");
        clearButton = new JButton("Clear");
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

        // Back button
        backButton = new JButton("Back to " + callerRole + " Dashboard");
        backButton.addActionListener(this);

        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(noticeList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == postButton)
        {
            String notice = noticeField.getText().trim();
            if (!notice.isEmpty())
            {
                notices.add(notice);
                listModel.addElement(notice);
                noticeField.setText("");
            }
        }
        else if (src == clearButton)
        {
            noticeField.setText("");
        }
        else if (src == backButton)
        {
            dispose();
            caller.setVisible(true);
        }
    }
}