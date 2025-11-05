package ui;

import dao.ClassStructureDAO;
import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class UserManagementDirectorySwing extends JFrame implements ActionListener {
    private JTree classTree;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton addUserButton, addYearButton, addSectionButton, refreshButton, backButton;
    private String adminUsername;

    public UserManagementDirectorySwing(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("CampusConnect - User Management");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("User Management Explorer", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // Tree Panel
        classTree = new JTree();
        classTree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        classTree.addTreeSelectionListener(e -> handleTreeSelection());
        classTree.addMouseListener(new TreeContextMenu());

        JScrollPane treeScroll = new JScrollPane(classTree);
        treeScroll.setPreferredSize(new Dimension(280, 500));

        // Table Panel
        String[] columns = {"Username", "Roll No", "Name", "Phone", "Email", "Role", "Edit"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        userTable.setDefaultEditor(Object.class, null);

        userTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = userTable.rowAtPoint(e.getPoint());
                int col = userTable.columnAtPoint(e.getPoint());
                if (col == 6) {
                    String username = userTable.getValueAt(row, 0).toString();
                    Integer userId = new UserDAO().getUserIdByUsername(username);
                    if (userId != null) {
                        new UserMEditPopupSwing(userId);
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found.");
                    }
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(userTable);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, tableScroll);
        add(splitPane, BorderLayout.CENTER);

        // Top Buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        addUserButton = new JButton("Add User");
        addYearButton = new JButton("Add Academic Year");
        addSectionButton = new JButton("Add Section");
        refreshButton = new JButton("Refresh Tree");

        addUserButton.setEnabled(false);
        addYearButton.setEnabled(false);
        addSectionButton.setEnabled(false);

        addUserButton.addActionListener(this);
        addYearButton.addActionListener(this);
        addSectionButton.addActionListener(this);
        refreshButton.addActionListener(e -> loadTree());

        topPanel.add(addUserButton);
        topPanel.add(addYearButton);
        topPanel.add(addSectionButton);
        topPanel.add(refreshButton);
        add(topPanel, BorderLayout.SOUTH);

        // Bottom: Back Button
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            dispose();
            new AdminDashboardSwing(adminUsername);
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTree();
        setVisible(true);
    }

    private void loadTree() {
        ClassStructureDAO dao = new ClassStructureDAO();
        Map<String, Map<String, Map<String, List<String>>>> treeData = dao.getClassTree();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Branches");
        for (String branch : treeData.keySet()) {
            DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode(branch);
            Map<String, Map<String, List<String>>> yearsMap = treeData.get(branch);

            for (String academicYear : yearsMap.keySet()) {
                DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode(academicYear);
                Map<String, List<String>> yearSections = yearsMap.get(academicYear);

                for (String year : yearSections.keySet()) {
                    DefaultMutableTreeNode classYearNode = new DefaultMutableTreeNode(year);
                    for (String section : yearSections.get(year)) {
                        classYearNode.add(new DefaultMutableTreeNode(section));
                    }
                    yearNode.add(classYearNode);
                }
                branchNode.add(yearNode);
            }
            root.add(branchNode);
        }

        classTree.setModel(new DefaultTreeModel(root));
        for (int i = 0; i < classTree.getRowCount(); i++) {
            classTree.expandRow(i);
        }
    }

    private void handleTreeSelection() {
        TreePath path = classTree.getSelectionPath();
        if (path == null || path.getPathCount() < 4) {
            addUserButton.setEnabled(false);
            addYearButton.setEnabled(path != null && path.getPathCount() == 2);
            addSectionButton.setEnabled(path != null && path.getPathCount() == 3);
            tableModel.setRowCount(0);
            return;
        }

        addUserButton.setEnabled(path.getPathCount() == 5);
        addYearButton.setEnabled(false);
        addSectionButton.setEnabled(false);

        if (path.getPathCount() == 5) {
            String branch = path.getPathComponent(1).toString();
            String academicYear = path.getPathComponent(2).toString();
            String year = path.getPathComponent(3).toString();
            String section = path.getPathComponent(4).toString();

            List<User> users = new UserDAO().getUsersByClass(branch, year, section, academicYear);
            tableModel.setRowCount(0);
            for (User u : users) {
                tableModel.addRow(new Object[]{
                        u.getUsername(), u.getRollNumber(), u.getName(),
                        u.getPhone(), u.getEmail(), u.getRole(), "Edit"
                });
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = classTree.getSelectionPath();
        if (path == null) return;

        if (e.getSource() == addUserButton && path.getPathCount() == 5) {
            String branch = path.getPathComponent(1).toString();
            String academicYear = path.getPathComponent(2).toString();
            String year = path.getPathComponent(3).toString();
            String section = path.getPathComponent(4).toString();
            new UserMAddPopupSwing(branch, academicYear, year, section);
        } else if (e.getSource() == addYearButton && path.getPathCount() == 2) {
            String branch = path.getPathComponent(1).toString();
            new UserMAcademicYearAddPopupSwing(branch);
        } else if (e.getSource() == addSectionButton && path.getPathCount() == 3) {
            String branch = path.getPathComponent(1).toString();
            String academicYear = path.getPathComponent(2).toString();
            new UserMSectionAddPopupSwing(branch, academicYear);
        }
    }

    private class TreeContextMenu extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                TreePath path = classTree.getPathForLocation(e.getX(), e.getY());
                if (path != null && path.getPathCount() >= 3) {
                    classTree.setSelectionPath(path);
                    JPopupMenu menu = new JPopupMenu();

                    if (path.getPathCount() == 3) {
                        JMenuItem deleteYear = new JMenuItem("Delete Academic Year");
                        deleteYear.addActionListener(ev -> {
                            String branch = path.getPathComponent(1).toString();
                            String academicYear = path.getPathComponent(2).toString();
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Delete academic year " + academicYear + " under " + branch + "?",
                                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                new ClassStructureDAO().deleteAcademicYear(branch, academicYear);
                                loadTree();
                            }
                        });
                        menu.add(deleteYear);
                    } else if (path.getPathCount() == 4) {
                        JMenuItem deleteSection = new JMenuItem("Delete Section");
                        deleteSection.addActionListener(ev -> {
                            String branch = path.getPathComponent(1).toString();
                            String academicYear = path.getPathComponent(2).toString();
                            String year = path.getPathComponent(3).toString();
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Delete year " + year + " under " + academicYear + " (" + branch + ")?",
                                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                new ClassStructureDAO().deleteSection(branch, academicYear, year);
                                loadTree();
                            }
                        });
                        menu.add(deleteSection);
                    }

                    menu.show(classTree, e.getX(), e.getY());
                }
            }
        }
    }
}
