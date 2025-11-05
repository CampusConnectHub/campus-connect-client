package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMSectionAddPopupSwing extends JFrame implements ActionListener {
    private JTextField sectionField, yearField;
    private JButton saveButton, cancelButton;
    private String branch, academicYear;

    public UserMSectionAddPopupSwing(String branch, String academicYear) {
        this.branch = branch;
        this.academicYear = academicYear;

        setTitle("Add Section to " + branch + " (" + academicYear + ")");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        yearField = new JTextField();
        sectionField = new JTextField();
        saveButton = new JButton("Add Section");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(new JLabel("Enter Year (e.g., 1, 2, 3, 4):", SwingConstants.CENTER));
        add(yearField);
        add(new JLabel("Enter Section (e.g., A, B, C):", SwingConstants.CENTER));
        add(sectionField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String year = yearField.getText().trim();
        String section = sectionField.getText().trim();

        if (year.isEmpty() || section.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Year and Section cannot be empty.");
            return;
        }

        boolean success = new ClassStructureDAO().addSection(branch, academicYear, year, section);
        JOptionPane.showMessageDialog(this, success ? "Section added!" : "Failed to add section.");
        if (success) dispose();
    }
}
