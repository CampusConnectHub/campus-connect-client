package ui;

import dao.EventDAO;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EventViewerSwing extends JFrame implements ActionListener {
    private JList<String> eventList;
    private DefaultListModel<String> listModel;
    private JButton backButton;
    private JFrame caller;

    public EventViewerSwing(JFrame caller) {
        this.caller = caller;

        setTitle("CampusConnect - Event Viewer");

        // Resize and center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        backButton = new JButton("Back to Student Dashboard");
        backButton.addActionListener(this);

        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        loadEvents();
        setVisible(true);
    }

    private void loadEvents() {
        listModel.clear();
        List<Event> events = new EventDAO().getAllEvents();
        for (Event e : events) {
            listModel.addElement(e.getTitle() + " - " + e.getDescription());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        caller.setVisible(true);
    }
}
