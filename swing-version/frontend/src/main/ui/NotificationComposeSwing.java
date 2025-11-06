package ui;

import model.NotificationService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class NotificationComposeSwing extends JFrame {
    private JTextField titleField;
    private JTextArea bodyArea;
    private JComboBox<String> priorityBox;
    private JCheckBox sendNowBox;
    private JSpinner scheduleSpinner, expirySpinner;
    private JComboBox<String> expiryOptionBox;
    private JButton attachButton, sendButton, emailAlertButton, cancelButton;

    private JComboBox<String> audienceTypeBox;
    private JTextField audienceInputField;
    private DefaultListModel<String> audienceListModel;
    private JList<String> audienceList;

    private List<String> attachments = new ArrayList<>();
    private String username, role;

    public NotificationComposeSwing(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("Compose Notification");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title and Priority
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(new JLabel("Title / Subject:"));
        titleField = new JTextField();
        topPanel.add(titleField);

        topPanel.add(new JLabel("Priority:"));
        priorityBox = new JComboBox<>(new String[]{"Normal", "Urgent"});
        topPanel.add(priorityBox);

        add(topPanel, BorderLayout.NORTH);

        // Body
        bodyArea = new JTextArea(8, 40);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        JScrollPane bodyScroll = new JScrollPane(bodyArea);
        bodyScroll.setBorder(BorderFactory.createTitledBorder("Message Body"));
        add(bodyScroll, BorderLayout.CENTER);

        // Audience + Delivery
        JPanel middlePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Audience
        JPanel audiencePanel = new JPanel(new BorderLayout(5, 5));
        audiencePanel.setBorder(BorderFactory.createTitledBorder("Target Audience"));

        audienceTypeBox = new JComboBox<>(new String[]{
                "All Users", "Role", "Branch", "Academic Year", "Year", "Section", "Usernames"
        });
        audienceInputField = new JTextField();
        JButton addAudienceButton = new JButton("Add");
        audienceListModel = new DefaultListModel<>();
        audienceList = new JList<>(audienceListModel);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(audienceTypeBox, BorderLayout.WEST);
        inputPanel.add(audienceInputField, BorderLayout.CENTER);
        inputPanel.add(addAudienceButton, BorderLayout.EAST);

        audiencePanel.add(inputPanel, BorderLayout.NORTH);
        audiencePanel.add(new JScrollPane(audienceList), BorderLayout.CENTER);

        addAudienceButton.addActionListener(e -> {
            String type = audienceTypeBox.getSelectedItem().toString();
            String value = audienceInputField.getText().trim();
            if (!value.isEmpty()) {
                audienceListModel.addElement(type + ": " + value);
                audienceInputField.setText("");
            }
        });

        // Delivery
        JPanel deliveryPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        deliveryPanel.setBorder(BorderFactory.createTitledBorder("Delivery Options"));

        sendNowBox = new JCheckBox("Send Immediately", true);
        scheduleSpinner = new JSpinner(new SpinnerDateModel());
        expiryOptionBox = new JComboBox<>(new String[]{"None", "Pick Date"});
        expirySpinner = new JSpinner(new SpinnerDateModel());

        deliveryPanel.add(new JLabel("Send Immediately:"));
        deliveryPanel.add(sendNowBox);
        deliveryPanel.add(new JLabel("Schedule For:"));
        deliveryPanel.add(scheduleSpinner);
        deliveryPanel.add(new JLabel("Expiry Option:"));
        deliveryPanel.add(expiryOptionBox);
        deliveryPanel.add(new JLabel("Expiry Date:"));
        deliveryPanel.add(expirySpinner);

        middlePanel.add(audiencePanel);
        middlePanel.add(deliveryPanel);
        add(middlePanel, BorderLayout.EAST);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        attachButton = new JButton("Attach File");
        sendButton = new JButton("Publish Notification");
        emailAlertButton = new JButton("Send Email Notification Alerts");
        cancelButton = new JButton("Cancel");

        bottomPanel.add(attachButton);
        bottomPanel.add(sendButton);
        bottomPanel.add(emailAlertButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        sendNowBox.addItemListener(e -> {
            boolean sendNow = sendNowBox.isSelected();
            scheduleSpinner.setEnabled(!sendNow);
        });

        expiryOptionBox.addItemListener(e -> {
            String option = (String) expiryOptionBox.getSelectedItem();
            expirySpinner.setEnabled("Pick Date".equals(option));
        });

        attachButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("PDF and Images", "pdf", "jpg", "png"));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                attachments.add(chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Attached: " + chooser.getSelectedFile().getName());
            }
        });

        sendButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String body = bodyArea.getText().trim();
            String priority = priorityBox.getSelectedItem().toString();
            boolean sendNow = sendNowBox.isSelected();
            Timestamp sendAt = new Timestamp(((Date) scheduleSpinner.getValue()).getTime());
            Timestamp expiryAt = null;
            if ("Pick Date".equals(expiryOptionBox.getSelectedItem())) {
                expiryAt = new Timestamp(((Date) expirySpinner.getValue()).getTime());
            }

            // Audience resolution
            String audienceType = "All Users";
            StringBuilder payloadBuilder = new StringBuilder();
            for (int i = 0; i < audienceListModel.size(); i++) {
                String entry = audienceListModel.get(i);
                String[] parts = entry.split(":");
                if (i == 0) audienceType = parts[0].trim();
                payloadBuilder.append(parts[1].trim()).append(",");
            }
            String audiencePayload = payloadBuilder.toString().replaceAll(",$", "");

            // Call service
            NotificationService service = new NotificationService();
            int notificationId = service.publishNotification(
                    title, body, priority,
                    username, role,
                    audienceType, audiencePayload,
                    sendNow, sendAt, expiryAt,
                    null // attachments not yet handled
            );

            if (notificationId != -1) {
                JOptionPane.showMessageDialog(this, "Notification published successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to publish notification.");
            }
        });

        emailAlertButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Feature not yet released!");
        });

        cancelButton.addActionListener(e -> dispose());

        scheduleSpinner.setEnabled(!sendNowBox.isSelected());
        expirySpinner.setEnabled("Pick Date".equals(expiryOptionBox.getSelectedItem()));

        setVisible(true);
    }
}
