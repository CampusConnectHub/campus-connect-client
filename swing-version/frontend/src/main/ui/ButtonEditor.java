package ui;

import model.SubjectProjectConfig;
import dao.SubjectProjectDAO;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String facultyUsername;
    private String subjectName;

    public ButtonEditor(JCheckBox checkBox, String facultyUsername) {
        super(checkBox);
        this.facultyUsername = facultyUsername;
        button = new JButton("Edit");
        button.addActionListener(e -> {
            SubjectProjectConfig config = new SubjectProjectDAO().getConfigBySubject(subjectName);
            new ProjectSubjectSetupSwing(facultyUsername, config); // You’ll need to support editing in setup class
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        subjectName = table.getValueAt(row, 0).toString();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Edit";
    }
}
