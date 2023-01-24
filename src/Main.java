import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {
        // TODO: SETUP
        window = new JFrame();
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Cool Social Media");

        // SET WINDOW ICON
        new Main().setIcon(window);

        // SAVE POSTS
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PostManager.savePosts();
            }
        }); // ------

        // TODO: PRIMARY PANEL : SIGN IN PANEL
        SignInPanel signInPanel = new SignInPanel();
        window.add(signInPanel);

        // SET VISIBLE
        Theme.lightTheme();
        window.setVisible(true);
    }

    public static void removePanel(JPanel panel) {
        window.remove(panel);
    }

    public void setIcon(JFrame window) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/window_icon.png"));
        window.setIconImage(icon.getImage());
    }
}