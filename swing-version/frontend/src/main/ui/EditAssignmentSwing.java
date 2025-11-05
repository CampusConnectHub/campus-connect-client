package ui;

import dao.AssignmentDAO;
import model.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditAssignmentSwing extends JFrame implements ActionListener {
    private JTextField titleField, dueDateField;
    private JTextArea descriptionArea;
    private JButton updateButton, cancelButton;
    private AssignmentPanelSwing parent;
    private Assignment assignment;

    public EditAssignmentSwing(Assignment assignment, AssignmentPanelSwing parent) {
        this.assignment = assignment;
        this.parent = parent;

        setTitle("Edit Assignment");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        titleField = new JTextField(assignment.getTitle());
        descriptionArea = new JTextArea(assignment.getDescription(), 3, 20);
        dueDateField = new JTextField(assignment.getDueDate());

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        formPanel.add(dueDateField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        updateButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == updateButton) {
            assignment.setTitle(titleField.getText().trim());
            assignment.setDescription(descriptionArea.getText().trim());
            assignment.setDueDate(dueDateField.getText().trim());

            boolean success = new AssignmentDAO().updateAssignment(assignment);

            if (success) {
                JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
                parent.refreshTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update assignment.");
            }
        } else if (src == cancelButton) {
            dispose();
        }
    }
}
