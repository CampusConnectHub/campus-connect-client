package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AttendancePanelSwing extends JFrame implements ActionListener
{
    private JButton markAttendanceButton, viewAttendanceButton, backButton;
    private String username;

    public AttendancePanelSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Attendance Panel");
        setSize(400, 250);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        markAttendanceButton = new JButton("Mark Attendance");
        viewAttendanceButton = new JButton("View Attendance");
        backButton = new JButton("Back to Dashboard");

        markAttendanceButton.addActionListener(this);
        viewAttendanceButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(markAttendanceButton);
        panel.add(viewAttendanceButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == markAttendanceButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: Mark attendance.");
        }
        else if (src == viewAttendanceButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View attendance records.");
        }
        else if (src == backButton)
        {
            dispose();
            new FacultyDashboardSwing(username);
        }
    }
}