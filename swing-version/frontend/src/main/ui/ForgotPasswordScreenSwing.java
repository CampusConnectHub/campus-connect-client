package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ForgotPasswordScreenSwing extends JFrame implements ActionListener {
    private JTextField emailField;
    private JButton recoverButton, backButton;

    public ForgotPasswordScreenSwing() {
        setTitle("CampusConnect - Forgot Password");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        emailField = new JTextField();
        recoverButton = new JButton("Recover Password");
        backButton = new JButton("Back to Login");

        recoverButton.addActionListener(this);
        backButton.addActionListener(this);

        add(new JLabel("Enter your registered email:"));
        add(emailField);
        add(recoverButton);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == recoverButton) {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your email.");
                return;
            }

            UserDAO dao = new UserDAO();
            if (dao.isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(this, "Admin has been notified. You will receive your password via email.");
                dispose();
                new LoginScreenSwing();
            } else {
                JOptionPane.showMessageDialog(this, "Email doesn't exist! ");
                int choice = JOptionPane.showConfirmDialog(this, "Would you like to Sign Up?", "Email Not Found", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    new SignUpScreenSwing();
                }
            }
        } else if (src == backButton) {
            dispose();
            new LoginScreenSwing();
        }
    }
}
