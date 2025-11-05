package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceCalendarSwing {
    private JComboBox<String> dayBox, monthBox, yearBox;
    private JCheckBox currentDateCheck, holidayCheck;
    private JButton acceptButton, cancelButton;
    private JLabel selectedDateLabel;
    private AttendancePanelSwing parent;
    private String username, role;
    private String selectedDate = "";

    public AttendanceCalendarSwing(AttendancePanelSwing parent, String username, String role) {
        this.parent = parent;
        this.username = username;
        this.role = role;

        JFrame frame = new JFrame("Select Attendance Date");
        frame.setSize(480, 360);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Main top panel with vertical stacking
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Date selection panel with horizontal layout
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dayBox = new JComboBox<>();
        monthBox = new JComboBox<>();
        yearBox = new JComboBox<>();

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        Month currentMonth = now.getMonth();

        for (Month m : Month.values()) {
            monthBox.addItem(m.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }

        for (int y = currentYear - 1; y <= currentYear + 1; y++) {
            yearBox.addItem(String.valueOf(y));
        }

        if (role.equalsIgnoreCase("FACULTY")) {
            monthBox.setSelectedItem(currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            monthBox.setEnabled(false);
            yearBox.setSelectedItem(String.valueOf(currentYear));
            yearBox.setEnabled(false);
        }

        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayBox);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthBox);
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearBox);

        // Checkbox panel with vertical layout
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        currentDateCheck = new JCheckBox("Use Current Date");
        checkboxPanel.add(currentDateCheck);

        String holidayLabel;
        if (role.equalsIgnoreCase("ADMIN")) {
            holidayLabel = "Mark this day as College Holiday";
        } else {
            holidayLabel = "Request Holiday for this day";
        }
        holidayCheck = new JCheckBox(holidayLabel);
        checkboxPanel.add(holidayCheck);

        currentDateCheck.addItemListener(e -> {
            boolean selected = currentDateCheck.isSelected();
            dayBox.setEnabled(!selected);
            if (!role.equalsIgnoreCase("FACULTY")) {
                monthBox.setEnabled(!selected);
                yearBox.setEnabled(!selected);
            }

            if (selected) {
                LocalDate today = LocalDate.now();
                String monthName = today.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                selectedDate = String.format("%02d/%s/%04d", today.getDayOfMonth(), monthName, today.getYear());
                selectedDateLabel.setText("Date Chosen: " + selectedDate);
            } else {
                updateSelectedDateLabel();
            }
        });

        monthBox.addActionListener(e -> {
            updateDays();
            updateSelectedDateLabel();
        });

        yearBox.addActionListener(e -> {
            updateDays();
            updateSelectedDateLabel();
        });

        dayBox.addActionListener(e -> updateSelectedDateLabel());

        topPanel.add(datePanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(checkboxPanel);

        selectedDateLabel = new JLabel("Date Chosen: None");
        selectedDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectedDateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        acceptButton = new JButton("Accept");
        cancelButton = new JButton("Cancel");

        acceptButton.addActionListener(e -> {
            if (!currentDateCheck.isSelected()) {
                updateSelectedDateLabel();
            }

            if (!selectedDate.isEmpty() && parent != null) {
                boolean isHoliday = holidayCheck.isSelected();
                parent.updateDate(selectedDate, isHoliday);
            }
            frame.dispose();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(selectedDateLabel);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(buttonPanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateDays();
        updateSelectedDateLabel();
        frame.setVisible(true);
    }

    private void updateDays() {
        dayBox.removeAllItems();
        try {
            int year = Integer.parseInt((String) yearBox.getSelectedItem());
            int month = monthBox.getSelectedIndex() + 1;
            int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
            for (int d = 1; d <= daysInMonth; d++) {
                dayBox.addItem(String.valueOf(d));
            }
        } catch (Exception ignored) {
        }
    }

    private void updateSelectedDateLabel() {
        if (!currentDateCheck.isSelected()) {
            try {
                int day = Integer.parseInt((String) dayBox.getSelectedItem());
                int monthIndex = monthBox.getSelectedIndex() + 1;
                int year = Integer.parseInt((String) yearBox.getSelectedItem());
                String monthName = Month.of(monthIndex).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                selectedDate = String.format("%02d/%s/%04d", day, monthName, year);
                selectedDateLabel.setText("Date Chosen: " + selectedDate);
            } catch (Exception e) {
                selectedDateLabel.setText("Date Chosen: None");
            }
        }
    }

//    public static void main(String[] args) {
//        new AttendanceCalendarSwing(null, "rishit", "ADMIN");
//    }
}
