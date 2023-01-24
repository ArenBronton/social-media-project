import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignInPanel extends JPanel {

    // TODO: COMPONENTS
    JLabel userIDLabel;
    JLabel passwordLabel;
    JTextField userIDField;
    JPasswordField passwordField;
    JButton signInButton;
    JButton signUpButton;
    JCheckBox revealPasswordCheckBox;

    // TODO: IMAGES
    ImageIcon checkBoxIcon1 = new ImageIcon(getClass().getClassLoader().getResource("icons/blind_eye.png"));
    ImageIcon checkBoxIcon2 = new ImageIcon(getClass().getClassLoader().getResource("icons/eye.png"));

    public SignInPanel() {
        setLayout(new BorderLayout());

        // TODO: TOP BAR
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Cool Social Media");
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

        signInButton = new JButton("Sign In ", new ImageIcon(getClass().getClassLoader().getResource("icons/login_icon.png")));
        signInButton.setBackground(Color.LIGHT_GRAY);
        signInButton.setBorder(new LineBorder(Color.GRAY, 1));
        signUpButton = new JButton("Create Account ", new ImageIcon(getClass().getClassLoader().getResource("icons/create_account_icon.png")));
        signUpButton.setBackground(Color.LIGHT_GRAY);
        signUpButton.setBorder(new LineBorder(Color.GRAY, 1));

        revealPasswordCheckBox = new JCheckBox(checkBoxIcon1, true);
        revealPasswordCheckBox.setSelected(true);

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
        centerPanel.add(signInButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(signUpButton, gbc);

        // TODO: ADD INTERNAL PANELS
        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public boolean checkSignIn(String userID, String password) {
        if (SignInManager.userSignIns.containsKey(userID)) {
            System.out.println("Found user");
            return SignInManager.userSignIns.get(userID).equals(password);
        }
        System.out.println("Couldn't sign in");
        return false;
    }

    public void removeThisPanel() {
        Main.removePanel(this);
    }

    public void setUpListeners() {
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // DEBUG // System.out.println(userIDField.getText() + "," + passwordField.getText());
                if (checkSignIn(userIDField.getText(), passwordField.getText())) {
                    User.init(userIDField.getText(), passwordField.getText());
                    setVisible(false);
                    removeThisPanel();
                    Main.window.add(new MainPanel());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid UserID or Password");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                removeThisPanel();
                Main.window.add(new SignUpPanel());
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

        revealPasswordCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        signInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                signInButton.setBackground(new Color(159, 159, 159));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                signInButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                signUpButton.setBackground(new Color(159, 159, 159));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                signUpButton.setBackground(Color.LIGHT_GRAY);
            }
        });
    }

}
