package ui;

import dao.NoticeDAO;
import model.Notice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NoticeBoardSwing extends JFrame implements ActionListener {
    private JTextField noticeField;
    private JButton postButton, clearButton, backButton;
    private JList<String> noticeList;
    private DefaultListModel<String> listModel;
    private JFrame caller;
    private String callerRole;

    public NoticeBoardSwing(JFrame caller, String role) {
        this.caller = caller;
        this.callerRole = role;

        setTitle("CampusConnect - Notice Board");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        noticeList = new JList<>(listModel);
        noticeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        noticeField = new JTextField();
        postButton = new JButton("Post");
        clearButton = new JButton("Clear");

        postButton.addActionListener(this);
        clearButton.addActionListener(this);

        formPanel.add(new JLabel("Enter Notice:"));
        formPanel.add(noticeField);
        formPanel.add(postButton);
        formPanel.add(clearButton);

        if (callerRole.equals("Student")) {
            noticeField.setEnabled(false);
            postButton.setEnabled(false);
            clearButton.setEnabled(false);
        }

        backButton = new JButton("Back to " + callerRole + " Dashboard");
        backButton.addActionListener(this);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(noticeList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        loadNotices();
        setVisible(true);
    }

    private void loadNotices() {
        listModel.clear();
        List<Notice> notices = new NoticeDAO().getAllNotices();
        for (Notice n : notices) {
            listModel.addElement(n.getContent());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == postButton) {
            String content = noticeField.getText().trim();
            if (!content.isEmpty()) {
                Notice notice = new Notice(0, content);
                boolean success = new NoticeDAO().postNotice(notice);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Notice posted!");
                    noticeField.setText("");
                    loadNotices();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to post notice.");
                }
            }
        } else if (src == clearButton) {
            noticeField.setText("");
        } else if (src == backButton) {
            dispose();
            caller.setVisible(true);
        }
    }
}
