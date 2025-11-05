package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignmentSubmissionSwing extends JFrame implements ActionListener {
    private JButton viewAssignmentsButton, submitAssignmentButton, backButton;
    private String username;

    public AssignmentSubmissionSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - Assignment Submission");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height * 2 / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel headingLabel = new JLabel("Assignment Submission Panel", SwingConstants.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        viewAssignmentsButton = createButton("View Assignments", "Check your assignment status");
        submitAssignmentButton = createButton("Submit Assignment", "Upload and submit your assignment");
        backButton = createButton("Back to Dashboard", "Return to student dashboard");

        JPanel gridPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        gridPanel.add(viewAssignmentsButton);
        gridPanel.add(submitAssignmentButton);
        gridPanel.add(backButton);

        add(gridPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(200, 60));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewAssignmentsButton) {
            setVisible(false);
            new AssignmentViewPanelSwing(username);
        } else if (src == submitAssignmentButton) {
            setVisible(false);
            new AssignmentSubmitPanelSwing(username);
        } else if (src == backButton) {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}
