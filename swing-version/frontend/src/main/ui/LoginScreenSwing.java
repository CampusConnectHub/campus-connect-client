package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreenSwing extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleChoice;
    private JButton loginButton, signUpButton, forgotPasswordButton;
    private JCheckBox showPasswordCheckbox;

    public LoginScreenSwing() {
        setTitle("CampusConnect - Login");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel(""));
        showPasswordCheckbox = new JCheckBox("Show Password");
        panel.add(showPasswordCheckbox);

        panel.add(new JLabel("Role:"));
        roleChoice = new JComboBox<>(new String[]{"Admin", "Faculty", "Student"});
        panel.add(roleChoice);

        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        forgotPasswordButton = new JButton("Forgot Password");

        loginButton.addActionListener(this);
        signUpButton.addActionListener(this);
        forgotPasswordButton.addActionListener(this);

        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(new JLabel(""));
        panel.add(forgotPasswordButton);

        showPasswordCheckbox.addItemListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '•');
        });

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == loginButton) {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            String role = (String) roleChoice.getSelectedItem();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Login Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dispose();
            switch (role) {
                case "Admin":
                    new AdminDashboardSwing(user);
                    break;
                case "Faculty":
                    new FacultyDashboardSwing(user);
                    break;
                case "Student":
                    new StudentDashboardSwing(user);
                    break;
            }
        } else if (src == signUpButton) {
            dispose();
            new SignUpScreenSwing();
        } else if (src == forgotPasswordButton) {
            dispose();
            new ForgotPasswordScreenSwing();
        }
    }
}
