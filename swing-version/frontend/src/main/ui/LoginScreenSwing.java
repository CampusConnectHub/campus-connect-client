package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreenSwing extends JFrame implements ActionListener
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleChoice;
    private JButton loginButton;
    private JCheckBox showPasswordCheckbox;

    public LoginScreenSwing()
    {
        setTitle("CampusConnect - Login");
        setSize(400, 300);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        panel.add(new JLabel(""));
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        showPasswordCheckbox.addItemListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '•');
        });

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String role = (String) roleChoice.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Login Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose();
        switch (role)
        {
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
    }
}