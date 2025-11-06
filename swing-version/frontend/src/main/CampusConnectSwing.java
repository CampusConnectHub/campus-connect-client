//MAIN STARTING
import javax.swing.SwingUtilities;
import ui.LoginScreenSwing; // Swing version of LoginScreen

public class CampusConnectSwing
{
    public static void main(String[] args)
    {
        // Ensure GUI runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginScreenSwing(); // Launch Swing-based login screen
        });
    }
}