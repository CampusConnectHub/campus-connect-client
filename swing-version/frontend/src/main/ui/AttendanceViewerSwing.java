package ui;

import dao.AttendanceDAO;
import model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AttendanceViewerSwing extends JFrame implements ActionListener {
    private JButton viewAttendanceButton, backButton;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private String username;

    public AttendanceViewerSwing(String username) {
        this.username = username;

        setTitle("CampusConnect - My Attendance");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 2 / 3;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        viewAttendanceButton = new JButton("View My Attendance");
        backButton = new JButton("Back to Dashboard");

        viewAttendanceButton.addActionListener(this);
        backButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(viewAttendanceButton);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Date", "Present", "Academic Year", "Branch", "Year", "Section"}, 0
        );
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadStudentAttendance() {
        tableModel.setRowCount(0);
        List<Attendance> list = new AttendanceDAO().getAttendanceByStudent(username);
        for (Attendance a : list) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getDate(),
                    a.isPresent() ? "Yes" : "No",
                    a.getAcademicYear(),
                    a.getBranch(),
                    a.getYear(),
                    a.getSection()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == viewAttendanceButton) {
            loadStudentAttendance();
        } else if (src == backButton) {
            dispose();
            new StudentDashboardSwing(username);
        }
    }
}
