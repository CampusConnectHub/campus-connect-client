package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AttendanceViewerSwing extends JFrame implements ActionListener
{
    private JButton viewAttendanceButton, backButton;
    private String username;

    public AttendanceViewerSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Attendance Viewer");
        setSize(400, 200);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewAttendanceButton = new JButton("View My Attendance");
        backButton = new JButton("Back to Dashboard");

        viewAttendanceButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(viewAttendanceButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == viewAttendanceButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View your attendance records.");
        }
        else if (src == backButton)
        {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}