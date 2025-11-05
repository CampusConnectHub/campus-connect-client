package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMAcademicYearAddPopupSwing extends JFrame implements ActionListener {
    private JTextField yearField;
    private JButton saveButton, cancelButton;
    private String branch;
    private Runnable onSuccessCallback;

    public UserMAcademicYearAddPopupSwing(String branch, Runnable onSuccessCallback) {
        this.branch = branch;
        this.onSuccessCallback = onSuccessCallback;

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

        add(new JLabel("Enter Academic Year (e.g., 2024-28):", SwingConstants.CENTER));
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

        boolean success = new ClassStructureDAO().addAcademicYearWithYears(branch, academicYear);
        JOptionPane.showMessageDialog(this,
                success ? "Academic year added with 4 years!" : "Failed to add academic year.");

        if (success) {
            dispose();
            if (onSuccessCallback != null) {
                SwingUtilities.invokeLater(onSuccessCallback);
            }
        }
    }
}
