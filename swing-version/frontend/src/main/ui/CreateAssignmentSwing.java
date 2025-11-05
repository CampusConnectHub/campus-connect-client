package ui;

import dao.AssignmentDAO;
import model.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAssignmentSwing extends JFrame implements ActionListener {
    private JTextField titleField, dueDateField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusBox;
    private JButton submitButton, cancelButton;
    private String username, role;
    private AssignmentPanelSwing parent;

    public CreateAssignmentSwing(String username, String role, AssignmentPanelSwing parent) {
        this.username = username;
        this.role = role;
        this.parent = parent;

        setTitle("Create New Assignment");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        dueDateField = new JTextField();
        statusBox = new JComboBox<>(new String[]{"Submitted", "Not Submitted"});

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        formPanel.add(dueDateField);
        formPanel.add(new JLabel("Submission Status:"));
        formPanel.add(statusBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == submitButton) {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String dueDate = dueDateField.getText().trim();
            String status = (String) statusBox.getSelectedItem();

            Assignment assignment = new Assignment(0, title, description, dueDate, status);
            boolean success = new AssignmentDAO().createAssignment(assignment);

            if (success) {
                JOptionPane.showMessageDialog(this, "Assignment created successfully!");
                parent.refreshTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create assignment.");
            }
        } else if (src == cancelButton) {
            dispose();
        }
    }
}
