package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserManagementEditSectionPopupSwing extends JFrame implements ActionListener {
    private JTextField sectionField;
    private JButton saveButton, cancelButton;
    private String branch, academicYear, year, oldSection;

    public UserManagementEditSectionPopupSwing(String branch, String academicYear, String year, String oldSection) {
        this.branch = branch;
        this.academicYear = academicYear;
        this.year = year;
        this.oldSection = oldSection;

        setTitle("Edit Section " + oldSection + " (" + year + " - " + academicYear + ")");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sectionField = new JTextField(oldSection);
        saveButton = new JButton("Update Section");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(new JLabel("Enter New Section Name:", SwingConstants.CENTER));
        add(sectionField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newSection = sectionField.getText().trim();
        if (newSection.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Section name cannot be empty.");
            return;
        }

        boolean deleted = new ClassStructureDAO().deleteSection(branch, academicYear, year);
        boolean added = new ClassStructureDAO().addSection(branch, academicYear, year, newSection);

        JOptionPane.showMessageDialog(this,
                deleted && added ? "Section updated!" : "Failed to update section.");
        if (deleted && added) dispose();
    }
}
