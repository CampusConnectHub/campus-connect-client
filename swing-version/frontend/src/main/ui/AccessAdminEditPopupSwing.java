package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class AccessAdminEditPopupSwing extends JDialog {
    private JTextField emailField, nameField;
    private JPasswordField passwordField;
    private JButton saveButton, cancelButton;
    private final int userId;
    private final Runnable onSuccess;

    public AccessAdminEditPopupSwing(int userId, Runnable onSuccess) {
        super((Frame) null, "Edit Admin", true);
        this.userId = userId;
        this.onSuccess = onSuccess;

        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        UserDAO dao = new UserDAO();
        User user = dao.getUserById(userId);
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            JOptionPane.showMessageDialog(this, "Admin not found.");
            dispose();
            return;
        }

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Username:"));
        JTextField usernameView = new JTextField(user.getUsername());
        usernameView.setEditable(false);
        form.add(usernameView);

        form.add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        form.add(emailField);

        form.add(new JLabel("Name:"));
        nameField = new JTextField(user.getName());
        form.add(nameField);

        form.add(new JLabel("New Password (optional):"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        actions.add(cancelButton);
        actions.add(saveButton);
        add(actions, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> handleSave());
    }

    private void handleSave() {
        UserDAO dao = new UserDAO();
        User existing = dao.getUserById(userId);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Admin not found.");
            return;
        }

        existing.setEmail(emailField.getText().trim());
        existing.setName(nameField.getText().trim());

        boolean updatedProfile = dao.updateUser(existing);
        boolean updatedPassword = false;

        String newPass = new String(passwordField.getPassword()).trim();
        if (!newPass.isEmpty()) {
            // Update password only if provided
            updatedPassword = dao.updatePasswordById(userId, newPass);
        }

        boolean ok = updatedProfile || updatedPassword;
        JOptionPane.showMessageDialog(this, ok ? "Admin updated." : "No changes or update failed.");
        if (ok && onSuccess != null) onSuccess.run();
        if (ok) dispose();
    }
}
