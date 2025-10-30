package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectTeamPanelSwing extends JFrame implements ActionListener
{
    private JButton createTeamButton, viewTeamButton, backButton;
    private String username;

    public ProjectTeamPanelSwing(String username)
    {
        this.username = username;

        setTitle("CampusConnect - Project Teams");
        setSize(400, 250);
        setLocation(350, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createTeamButton = new JButton("Create Team");
        viewTeamButton = new JButton("View My Teams");
        backButton = new JButton("Back to Dashboard");

        createTeamButton.addActionListener(this);
        viewTeamButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(createTeamButton);
        panel.add(viewTeamButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == createTeamButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: Create a new team.");
        }
        else if (src == viewTeamButton)
        {
            JOptionPane.showMessageDialog(this, "Feature coming soon: View your teams.");
        } else if (src == backButton)
        {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}