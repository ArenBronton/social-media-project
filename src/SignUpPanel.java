import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {

    // TODO: COMPONENTS
    JLabel userIDLabel;
    JLabel passwordLabel;
    JTextField userIDField;
    JPasswordField passwordField;
    JButton signUpButton;
    JCheckBox revealPasswordCheckBox;

    // TODO: IMAGES
    ImageIcon checkBoxIcon1 = new ImageIcon(getClass().getClassLoader().getResource("icons/blind_eye.png"));
    ImageIcon checkBoxIcon2 = new ImageIcon(getClass().getClassLoader().getResource("icons/eye.png"));

    public SignUpPanel() {
        setLayout(new BorderLayout());

        // TODO: TOP BAR
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Social Media");
        title.setFont(new Font("DIALOG", Font.BOLD, 18));

        topBar.add(title);

        // TODO: CENTER PANEL
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        userIDLabel = new JLabel("User ID: ");
        passwordLabel = new JLabel("Password: ");
        userIDField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('•');
        signUpButton = new JButton("Sign Up");
        revealPasswordCheckBox = new JCheckBox(checkBoxIcon1, true);

        setUpListeners();

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(userIDLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(userIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(passwordField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        centerPanel.add(revealPasswordCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(signUpButton, gbc);

        // TODO: ADD INTERNAL PANELS
        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void removeThisPanel() {
        Main.removePanel(this);
    }

    public void setUpListeners() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInManager.addUser(userIDField.getText(), passwordField.getText());

                setVisible(false);
                removeThisPanel();
                Main.window.add(new SignInPanel());
            }
        });

        revealPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (revealPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar('•');
                    revealPasswordCheckBox.setIcon(checkBoxIcon1);
                } else {
                    passwordField.setEchoChar((char)0);
                    revealPasswordCheckBox.setIcon(checkBoxIcon2);
                }
            }
        });
    }

}
