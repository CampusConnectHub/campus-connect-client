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
        setSize(1200, 700);
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
        treeScroll.setPreferredSize(new Dimension(300, 500));

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
        splitPane.setDividerLocation(320);
        add(splitPane, BorderLayout.CENTER);

        // Top Buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        addUserButton = new JButton("Add User");
        addYearButton = new JButton("Add Academic Year");
        addSectionButton = new JButton("Add Section");
        refreshButton = new JButton("Refresh Tree");
        JButton addBranchButton = new JButton("Add Branch");

        addUserButton.setEnabled(false);
        addYearButton.setEnabled(false);
        addSectionButton.setEnabled(false);

        addUserButton.addActionListener(this);
        addYearButton.addActionListener(this);
        addSectionButton.addActionListener(this);
        refreshButton.addActionListener(e -> loadTree());
        addBranchButton.addActionListener(e -> new UserMBranchAddPopupSwing(this::loadTree));

        topPanel.add(addBranchButton); // ✅ Added missing button
        topPanel.add(addUserButton);
        topPanel.add(addYearButton);
        topPanel.add(addSectionButton);
        topPanel.add(refreshButton);
        add(topPanel, BorderLayout.NORTH);

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
        System.out.println("Tree data: " + treeData);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Branches");

        if (treeData.isEmpty()) {
            root.add(new DefaultMutableTreeNode("No data available"));
        } else {
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
        }

        classTree.setModel(new DefaultTreeModel(root));
        ((DefaultTreeModel) classTree.getModel()).reload();

        for (int i = 0; i < classTree.getRowCount(); i++) {
            classTree.expandRow(i);
        }
    }


    private void handleTreeSelection() {
        TreePath path = classTree.getSelectionPath();
        if (path == null) {
            addUserButton.setEnabled(false);
            addYearButton.setEnabled(false);
            addSectionButton.setEnabled(false);
            tableModel.setRowCount(0);
            return;
        }

        int depth = path.getPathCount();
        addYearButton.setEnabled(depth == 2); // Branch selected
        addSectionButton.setEnabled(depth == 4); // Year selected (1, 2, 3, 4)
        addUserButton.setEnabled(depth == 5); // Full path selected

        tableModel.setRowCount(0);

        if (depth == 5) {
            String branch = path.getPathComponent(1).toString();
            String academicYear = path.getPathComponent(2).toString();
            String year = path.getPathComponent(3).toString();
            String section = path.getPathComponent(4).toString();

            List<User> users = new UserDAO().getUsersByClass(branch, year, section, academicYear);
            for (User u : users) {
                tableModel.addRow(new Object[]{
                        u.getUsername(), u.getRollNumber(), u.getName(),
                        u.getPhone(), u.getEmail(), u.getRole(), "Edit"
                });
            }
        }
    }

    private void handleDelete(TreePath path) {
        int depth = path.getPathCount();

        String branch = path.getPathComponent(1).toString();

        if (depth == 2) {
            // Delete branch
            int confirm = JOptionPane.showConfirmDialog(this, "Delete branch '" + branch + "'?");
            if (confirm == JOptionPane.YES_OPTION) {
                new ClassStructureDAO().deleteBranch(branch);
                loadTree();
            }
        } else if (depth == 3) {
            String academicYear = path.getPathComponent(2).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Delete academic year '" + academicYear + "'?");
            if (confirm == JOptionPane.YES_OPTION) {
                new ClassStructureDAO().deleteAcademicYear(branch, academicYear);
                loadTree();
            }
        } else if (depth == 4) {
            String academicYear = path.getPathComponent(2).toString();
            String year = path.getPathComponent(3).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Delete year '" + year + "'?");
            if (confirm == JOptionPane.YES_OPTION) {
                new ClassStructureDAO().deleteSection(branch, academicYear, year);
                loadTree();
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
            new UserMAcademicYearAddPopupSwing(branch, this::loadTree); // Refresh after adding year

        } else if (e.getSource() == addSectionButton && path.getPathCount() == 4) {
            String branch = path.getPathComponent(1).toString();
            String academicYear = path.getPathComponent(2).toString();
            String year = path.getPathComponent(3).toString();

            UserMSectionAddPopupSwing popup = new UserMSectionAddPopupSwing(branch, academicYear, year);
            popup.setOnSuccessCallback(this::loadTree); //  Refresh after adding section
        }
    }

    private class TreeContextMenu extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                TreePath path = classTree.getPathForLocation(e.getX(), e.getY());
                if (path != null && path.getPathCount() >= 2) {
                    classTree.setSelectionPath(path);
                    JPopupMenu menu = new JPopupMenu();

                    if (path.getPathCount() == 2) {
                        // Branch level
                        JMenuItem deleteBranch = new JMenuItem("Delete Branch");
                        deleteBranch.addActionListener(ev -> {
                            String branch = path.getPathComponent(1).toString();
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Delete branch '" + branch + "' and all its data?",
                                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                new ClassStructureDAO().deleteBranch(branch);
                                loadTree();
                            }
                        });
                        menu.add(deleteBranch);

                    } else if (path.getPathCount() == 3) {
                        // Academic year level
                        JMenuItem deleteYear = new JMenuItem("Delete Academic Year");
                        deleteYear.addActionListener(ev -> {
                            String branch = path.getPathComponent(1).toString();
                            String academicYear = path.getPathComponent(2).toString();
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Delete academic year '" + academicYear + "' under '" + branch + "'?",
                                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                new ClassStructureDAO().deleteAcademicYear(branch, academicYear);
                                loadTree();
                            }
                        });
                        menu.add(deleteYear);

                    } else if (path.getPathCount() == 4) {
                        // Year level
                        JMenuItem deleteSection = new JMenuItem("Delete Year (All Sections)");
                        deleteSection.addActionListener(ev -> {
                            String branch = path.getPathComponent(1).toString();
                            String academicYear = path.getPathComponent(2).toString();
                            String year = path.getPathComponent(3).toString();
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Delete all sections under year '" + year + "' in '" + academicYear + "' (" + branch + ")?",
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
