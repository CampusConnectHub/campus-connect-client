package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignmentDashboardSwing extends JFrame implements ActionListener {
    private JButton manageButton, reviewButton, backButton;
    private String username, role;

    public AssignmentDashboardSwing(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("CampusConnect - Assignment Dashboard");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        manageButton = new JButton("Manage Assignments");
        reviewButton = new JButton("Review Submissions");

        String backLabel = switch (role) {
            case "Admin" -> "Back to Admin Dashboard";
            case "Faculty" -> "Back to Faculty Dashboard";
            default -> "Back to Dashboard";
        };
        backButton = new JButton(backLabel);

        manageButton.addActionListener(this);
        reviewButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(manageButton);
        panel.add(reviewButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == manageButton) {
            new AssignmentPanelSwing(username, role, this);
            setVisible(false);
        } else if (src == reviewButton) {
            new SubmissionReviewSwing(username, role);
            setVisible(false);
        } else if (src == backButton) {
            dispose();
            switch (role) {
                case "Admin": new AdminDashboardSwing(username); break;
                case "Faculty": new FacultyDashboardSwing(username); break;
            }
        }
    }
}
