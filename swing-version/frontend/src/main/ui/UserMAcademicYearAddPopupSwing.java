package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMAcademicYearAddPopupSwing extends JFrame implements ActionListener {
    private JTextField yearField;
    private JButton saveButton, cancelButton;
    private String branch;

    public UserMAcademicYearAddPopupSwing(String branch) {
        this.branch = branch;

        setTitle("Add Academic Year to " + branch);
        setSize(350, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        yearField = new JTextField();
        saveButton = new JButton("Add Year");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(new JLabel("Enter Academic Year (e.g., 2024-25):", SwingConstants.CENTER));
        add(yearField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String academicYear = yearField.getText().trim();
        if (academicYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Academic year cannot be empty.");
            return;
        }

        boolean success = new ClassStructureDAO().addAcademicYear(branch, academicYear);
        JOptionPane.showMessageDialog(this, success ? "Academic year added!" : "Failed to add year.");
        if (success) dispose();
    }
}
