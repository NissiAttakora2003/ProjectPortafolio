
import javax.swing.*;

public class GUI_window_display {

    public void warning_messages(String message) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(window, message);
    }
}