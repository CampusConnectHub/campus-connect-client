package ui;

import dao.AttendanceDAO;
import model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceStudentSwing extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private String username, academicYear, branch, year, section, semester, date, role;
    private JButton backButton, saveButton, exportButton;

    public AttendanceStudentSwing(String username, String academicYear, String branch, String year, String section, String semester, String date, String role) {
        this.username = username;
        this.academicYear = academicYear;
        this.branch = branch;
        this.year = year;
        this.section = section;
        this.semester = semester;
        this.date = date;
        this.role = role;

        setTitle("Student Attendance - " + branch + " " + year + " " + section);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                openAttendancePanel();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                openAttendancePanel();
            }
        });

        tableModel = new DefaultTableModel(
                new Object[]{"User ID", "Name", "Roll No", "Present", "Timestamp", "Marked By"}, 0
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Attendance Panel");
        backButton.addActionListener(e -> dispose());

        saveButton = new JButton("Save Attendance");
        saveButton.addActionListener(e -> saveAttendanceToDatabase());

        exportButton = new JButton("Export to CSV");
        exportButton.addActionListener(e -> exportToCSV());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(exportButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadStudentsFromDatabase();
        setVisible(true);
    }

    private void openAttendancePanel() {
        new AttendancePanelSwing(username, role);
    }

    private void loadStudentsFromDatabase() {
        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> records = dao.getAttendanceByDateAndSection(date, branch, year, section, semester);
        for (Attendance a : records) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getStudentName(),
                    a.getSection(), // Replace with roll number if available
                    a.isPresent(),
                    a.getTimestamp() != null ? a.getTimestamp() : "",
                    a.getMarkedBy() != null ? a.getMarkedBy() : ""
            });
        }
    }

    private void saveAttendanceToDatabase() {
        AttendanceDAO dao = new AttendanceDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
            boolean present = (Boolean) tableModel.getValueAt(i, 3);
            String oldTimestamp = tableModel.getValueAt(i, 4).toString();

            if (oldTimestamp.isEmpty()) {
                String timestamp = LocalDateTime.now().format(formatter);
                tableModel.setValueAt(timestamp, i, 4);
                tableModel.setValueAt(username, i, 5);

                Attendance a = new Attendance(id, "", date, present, academicYear, branch, year, section);
                a.setTimestamp(timestamp);
                a.setMarkedBy(username);
                dao.updateAttendance(a);
            }
        }

        JOptionPane.showMessageDialog(this, "Attendance saved with timestamps!");
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Attendance as CSV");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave + ".csv")) {
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        fw.write(tableModel.getColumnName(i) + ",");
                    }
                    fw.write("\n");

                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        for (int j = 0; j < tableModel.getColumnCount(); j++) {
                            fw.write(tableModel.getValueAt(i, j).toString() + ",");
                        }
                        fw.write("\n");
                    }

                    JOptionPane.showMessageDialog(this, "CSV exported successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting CSV: " + ex.getMessage());
            }
        }
    }
}
