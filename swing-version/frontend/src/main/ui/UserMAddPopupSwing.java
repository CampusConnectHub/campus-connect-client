package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserMAddPopupSwing extends JFrame implements ActionListener {
    private JTextField usernameField, nameField, rollField, phoneField, emailField;
    private JComboBox<String> roleBox;
    private JButton saveButton, cancelButton;
    private String branch, academicYear, year, section;

    public UserMAddPopupSwing(String branch, String academicYear, String year, String section) {
        this.branch = branch;
        this.academicYear = academicYear;
        this.year = year;
        this.section = section;

        setTitle("Add User to Section " + section);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Roll No:"));
        rollField = new JTextField();
        add(rollField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"STUDENT", "FACULTY"});
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
        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String role = roleBox.getSelectedItem().toString();

        // Basic validation
        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || branch == null || year == null || section == null || academicYear == null) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        // Debug print
        System.out.println("Attempting to add user:");
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + roll);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        System.out.println("Branch: " + branch);
        System.out.println("Year: " + year);
        System.out.println("Section: " + section);
        System.out.println("Academic Year: " + academicYear);

        User user = new User(0, username, role, name);
        user.setRollNumber(roll);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBranch(branch);
        user.setYear(year);
        user.setSection(section);
        user.setAcademicYear(academicYear);

        boolean success = new UserDAO().addFullUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "User added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add user. Check console for details.");
        }
    }
}
