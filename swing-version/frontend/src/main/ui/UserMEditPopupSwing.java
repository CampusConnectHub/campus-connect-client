package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMEditPopupSwing extends JFrame implements ActionListener {
    private JTextField nameField, phoneField, emailField;
    private JComboBox<String> roleBox;
    private JButton saveButton, cancelButton;
    private int userId;

    public UserMEditPopupSwing(int userId) {
        this.userId = userId;
        User user = new UserDAO().getUserById(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found.");
            dispose();
            return;
        }

        setTitle("Edit User: " + user.getUsername());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Name:"));
        nameField = new JTextField(user.getName());
        add(nameField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField(user.getPhone());
        add(phoneField);

        add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        add(emailField);

        add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"STUDENT", "FACULTY"});
        roleBox.setSelectedItem(user.getRole());
        add(roleBox);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        add(saveButton);
        add(cancelButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User updated = new UserDAO().getUserById(userId);
        if (updated == null) {
            JOptionPane.showMessageDialog(this, "User not found.");
            dispose();
            return;
        }

        updated.setName(nameField.getText().trim());
        updated.setPhone(phoneField.getText().trim());
        updated.setEmail(emailField.getText().trim());
        updated.setRole(roleBox.getSelectedItem().toString());

        boolean success = new UserDAO().updateUser(updated);
        JOptionPane.showMessageDialog(this, success ? "User updated!" : "Update failed.");
        if (success) dispose();
    }
}
