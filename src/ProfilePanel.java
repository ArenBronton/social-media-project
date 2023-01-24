import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ProfilePanel extends JPanel {

    private static ArrayList<LeftSideButton> buttons = new ArrayList<LeftSideButton>();

    // TODO: PANELS
    JPanel topBar;
    JPanel centerPanel;
    JPanel leftPanel;

    // TODO: IMAGES
    ImageIcon lightThemeIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/light_theme_icon.png"));
    ImageIcon darkThemeIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/dark_theme_icon.png"));

    public ProfilePanel() {
        setLayout(new BorderLayout());






        // TODO: TOP BAR
        topBar = new JPanel();
        topBar.setBackground(new Color(166, 166, 166));
        JLabel title = new JLabel("YOUR PROFILE");
        title.setFont(new Font("DIALOG", Font.BOLD, 14));
        JButton backButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/back_icon.png")));
        backButton.setBackground(null);
        backButton.setBorder(null);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                removeThisPanel();
                Main.window.add(new MainPanel());
            }
        });
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        topBar.add(backButton);
        topBar.add(title);








        // TODO: CENTER PANEL
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        centerPanel = new JPanel(new GridBagLayout());

        JLabel generalPanelTitle = new JLabel("GENERAL SETTINGS");
        generalPanelTitle.setFont(new Font("DIALOG", Font.BOLD, 18));
        generalPanelTitle.setForeground(Theme.textColor2);
        generalPanelTitle.setHorizontalTextPosition(JLabel.CENTER);
        generalPanelTitle.setBorder(new MatteBorder(0, 0, 2, 0, Theme.textColor2));

        JLabel themeLabel = new JLabel("Theme: Light");
        themeLabel.setFont(new Font("DIALOG", Font.BOLD, 12));

        // THEME SWITCH
        JButton themeSwitch;
        if (Utilities.theme == Utilities.lightTheme) {
            themeSwitch = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/light_theme_icon.png")));
            themeSwitch.revalidate();
            themeSwitch.repaint();
            themeLabel.setText("Theme: Light");
        } else {
            themeSwitch = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/dark_theme_icon.png")));
            themeSwitch.revalidate();
            themeSwitch.repaint();
            themeLabel.setText("Theme: Dark");
        }

        themeSwitch.setSize(35, 35);
        themeSwitch.setBorder(null);
        themeSwitch.setBackground(null);
        themeSwitch.setFocusable(false);
        themeSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Utilities.theme == Utilities.lightTheme) {
                    Utilities.theme = Utilities.darkTheme;
                    themeLabel.setText("Theme: Dark");
                    themeSwitch.setIcon(darkThemeIcon);
                    Theme.darkTheme();
                } else {
                    Utilities.theme = Utilities.lightTheme;
                    themeLabel.setText("Theme: Light");
                    themeSwitch.setIcon(lightThemeIcon);
                    Theme.lightTheme();
                }
            }
        });
        themeSwitch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(generalPanelTitle, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(themeLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(themeSwitch, gbc);








        //TODO: LEFT PANEL
        leftPanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(0, 0,0 ,0);
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(200, 600));

        LeftSideButton generalButton = new LeftSideButton("General");
        ButtonContainer generalButtonContainer = new ButtonContainer(generalButton);

        LeftSideButton settingsButton = new LeftSideButton("Settings");
        ButtonContainer settingsButtonContainer = new ButtonContainer(settingsButton);
        settingsButtonContainer.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK)); //TODO: LAST BUTTOn

        buttons.add(generalButton);
        buttons.add(settingsButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(generalButtonContainer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(settingsButtonContainer, gbc);

        // TODO: ADD INTERNAL PANELS
        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
    }


    public static class LeftSideButton extends JLabel {
        private boolean selected = false;

        public LeftSideButton(String text) {
            setText(text);
            setSize(200, 50);
            setFont(new Font("DIALOG", Font.ROMAN_BASELINE, 25));
            setFocusable(false);
            setBackground(null);
            setBorder(null);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!selected) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        setBackground(Color.GRAY);
                        getParent().setBackground(new Color(175, 175, 175));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!selected) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        getParent().setBackground(null);
                        setBackground(getParent().getBackground());
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    for (LeftSideButton but : buttons) {
                        but.selected = false;
                        but.updateBackground();
                    }

                    selected = true;
                    updateBackground();

                }
            });
        }

        public void updateBackground() {
            if (selected) {
                setBackground(Color.GRAY);
                getParent().setBackground(Color.GRAY);
            } else {
                setBackground(null);
                getParent().setBackground(null);
            }
        }

    }

    public static class ButtonContainer extends JPanel {

        public ButtonContainer(JLabel internalButton) {
            setPreferredSize(new Dimension(200, 45));
            add(internalButton);
            setBackground(null);
            setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        }
    }







    public void removeThisPanel() {
        Main.removePanel(this);
    }
}
