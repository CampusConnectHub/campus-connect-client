package ui;

import dao.ClassStructureDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMBranchAddPopupSwing extends JFrame implements ActionListener {
    private JTextField branchField;
    private JButton saveButton, cancelButton;
    private Runnable onSuccessCallback;

    public UserMBranchAddPopupSwing(Runnable onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;

        setTitle("Add New Branch");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        branchField = new JTextField();
        saveButton = new JButton("Add Branch");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(new JLabel("Enter Branch Name:", SwingConstants.CENTER));
        add(branchField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String branch = branchField.getText().trim();
        if (branch.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Branch name cannot be empty.");
            return;
        }

        boolean success = new ClassStructureDAO().addBranch(branch);
        JOptionPane.showMessageDialog(this, success ? "Branch added!" : "Failed to add branch.");

        if (success) {
            dispose();
            if (onSuccessCallback != null) {
                SwingUtilities.invokeLater(onSuccessCallback); // ✅ Refresh tree
            }
        }
    }
}
