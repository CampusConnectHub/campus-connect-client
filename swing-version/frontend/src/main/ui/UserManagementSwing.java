package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserManagementSwing extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JComboBox<String> roleBox;
    private JButton addButton, deleteButton, updateButton, backButton;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private String adminUsername;

    public UserManagementSwing(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("CampusConnect - User Management");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3; // Increased width
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        usernameField = new JTextField();
        roleBox = new JComboBox<>(new String[]{"Student", "Faculty", "Admin"});
        addButton = new JButton("Add User");
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleBox);
        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getSelectionModel().addListSelectionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                usernameField.setText(tableModel.getValueAt(row, 1).toString());
                roleBox.setSelectedItem(tableModel.getValueAt(row, 2).toString());
            }
        });

        add(new JScrollPane(userTable), BorderLayout.CENTER);

        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(this);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadUsers();
        setVisible(true);
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = new UserDAO().getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == addButton) {
            String username = usernameField.getText().trim();
            String role = (String) roleBox.getSelectedItem();

            if (!username.isEmpty()) {
                User user = new User(0, username, role);
                boolean success = new UserDAO().addUser(user);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User added.");
                    usernameField.setText("");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add user.");
                }
            }
        } else if (src == updateButton) {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                int userId = (int) tableModel.getValueAt(row, 0);
                String username = usernameField.getText().trim();
                String role = (String) roleBox.getSelectedItem();

                if (!username.isEmpty()) {
                    User updatedUser = new User(userId, username, role);
                    boolean success = new UserDAO().updateUser(updatedUser);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "User updated.");
                        loadUsers();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update user.");
                    }
                }
            }
        } else if (src == deleteButton) {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                int userId = (int) tableModel.getValueAt(row, 0);
                boolean success = new UserDAO().deleteUserById(userId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User deleted.");
                    usernameField.setText("");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.");
                }
            }
        } else if (src == backButton) {
            dispose();
            new AdminDashboardSwing(adminUsername);
        }
    }
}
