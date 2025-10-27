package ui; //Directory
//Packages within JSL
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginScreen extends Frame implements ActionListener
{
    TextField usernameField;
    TextField passwordField;
    Choice roleChoice;
    Button loginButton;
    Checkbox showPasswordCheckbox;

    public LoginScreen()
    {
        setTitle("CampusConnect - Login");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2));
        setLocation(300, 200);

        add(new Label("Username:"));
        usernameField = new TextField();
        add(usernameField);

        add(new Label("Password:"));
        passwordField = new TextField();
        passwordField.setEchoChar('*');
        add(passwordField);

        add(new Label(""));
        showPasswordCheckbox = new Checkbox("Show Password");
        add(showPasswordCheckbox);

        add(new Label("Role:"));
        roleChoice = new Choice();
        roleChoice.add("Admin");
        roleChoice.add("Faculty");
        roleChoice.add("Student");
        add(roleChoice);

        loginButton = new Button("Login");
        loginButton.addActionListener(this);
        add(new Label());
        add(loginButton);

        showPasswordCheckbox.addItemListener(e ->
        {
            passwordField.setEchoChar(showPasswordCheckbox.getState() ? (char) 0 : '*');
        });

        setVisible(true);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();
        String role = roleChoice.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty())
        {
            System.out.println("Please enter both username and password.");
            return;
        }

        dispose();
        switch (role)
        {
            case "Admin":
                new AdminDashboardSwing(user);
                break;
            case "Faculty":
                new FacultyDashboard(user);
                break;
            case "Student":
                new StudentDashboard(user);
                break;
        }
    }
}