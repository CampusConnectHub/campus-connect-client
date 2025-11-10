package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class AccessAdminAddPopupSwing extends JDialog {
    private JTextField usernameField, emailField, nameField;
    private JPasswordField passwordField;
    private JButton addButton, cancelButton;
    private final Runnable onSuccess;

    public AccessAdminAddPopupSwing(Frame owner, Runnable onSuccess) {
        super(owner, "Add Admin", true);
        this.onSuccess = onSuccess;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);

        form.add(new JLabel("Email:"));
        emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        actions.add(cancelButton);
        actions.add(addButton);
        add(actions, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        addButton.addActionListener(e -> handleAdd());
    }

    private void handleAdd() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username, email, and password are required.");
            return;
        }

        UserDAO dao = new UserDAO();

        // Optional: check username uniqueness (since UNIQUE exists, this just gives a friendlier message)
        if (dao.getUserIdByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Choose another.");
            return;
        }

        boolean ok = dao.registerUser(email, username, password, "ADMIN", name);
        JOptionPane.showMessageDialog(this, ok ? "Admin added." : "Failed to add admin.");

        if (ok && onSuccess != null) onSuccess.run();
        if (ok) dispose();
    }
}
