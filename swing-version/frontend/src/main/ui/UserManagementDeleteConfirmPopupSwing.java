package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserManagementDeleteConfirmPopupSwing extends JFrame implements ActionListener {
    private JButton confirmButton, cancelButton;
    private Runnable onConfirm;

    public UserManagementDeleteConfirmPopupSwing(String message, Runnable onConfirm) {
        this.onConfirm = onConfirm;

        setTitle("Confirm Deletion");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel prompt = new JLabel(message, SwingConstants.CENTER);
        prompt.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(prompt, BorderLayout.CENTER);

        confirmButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (onConfirm != null) onConfirm.run();
        dispose();
    }
}
