package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpScreenSwing extends JFrame implements ActionListener {
    private JTextField emailField, usernameField, nameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton registerButton, backButton;

    public SignUpScreenSwing() {
        setTitle("CampusConnect - Sign Up");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10)); // Increased rows to 7

        emailField = new JTextField();
        usernameField = new JTextField();
        nameField = new JTextField(); // New name field
        passwordField = new JPasswordField();
        roleBox = new JComboBox<>(new String[]{"Student", "Faculty", "Admin"});
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        registerButton.addActionListener(this);
        backButton.addActionListener(this);

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role:"));
        add(roleBox);
        add(registerButton);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == registerButton) {
            String email = emailField.getText().trim();
            String username = usernameField.getText().trim();
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            UserDAO dao = new UserDAO();
            if (dao.getUserByUsername(username) != null) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
                return;
            }

            boolean success = dao.registerUser(email, username, password, role, name);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
                new LoginScreenSwing();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        } else if (src == backButton) {
            dispose();
            new LoginScreenSwing();
        }
    }
}
