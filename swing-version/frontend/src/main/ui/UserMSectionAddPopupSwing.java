package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMSectionAddPopupSwing extends JFrame implements ActionListener {
    private JTextField sectionField;
    private JButton saveButton, cancelButton;
    private String branch, academicYear, year;
    private Runnable onSuccessCallback;

    public UserMSectionAddPopupSwing(String branch, String academicYear, String year) {
        this.branch = branch;
        this.academicYear = academicYear;
        this.year = year;

        setTitle("Add Section to " + year + " (" + academicYear + ")");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sectionField = new JTextField();
        saveButton = new JButton("Add Section");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(new JLabel("Enter Section Name (e.g., A, B, C):", SwingConstants.CENTER));
        add(sectionField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        setVisible(true);
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String section = sectionField.getText().trim();

        if (section.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Section name cannot be empty.");
            return;
        }

        boolean success = new ClassStructureDAO().addSection(branch, academicYear, year, section);
        JOptionPane.showMessageDialog(this, success ? "Section added!" : "Failed to add section.");

        if (success) {
            dispose();
            if (onSuccessCallback != null) {
                SwingUtilities.invokeLater(onSuccessCallback);
            }
        }
    }
}
