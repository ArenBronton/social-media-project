import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainPanel extends JPanel {

    int postCount = 0;

    Font atLabelFont;

    // TODO: PANELS
    JPanel topBar;
    JPanel centerPanel;
    JPanel postsListPanel;
    JPanel rightPanel;

    // TODO: COMPONENTS
    // Top Bar
    JLabel topBarNameLabel;
    JButton topBarProfileButton;
    JButton topBarLogoutButton;
    // CENTER PANEL
    JScrollPane postListScrollPane;

    public MainPanel(){

        // SETUP FONTS
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Louis George Cafe Light.ttf");
            atLabelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBackground(Theme.defaultBackground);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 8, 0, 8);

        // TODO: TOP BAR
        topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(getWidth(), 30));
        topBar.setBackground(Theme.defaultForeground);

        topBarNameLabel = new JLabel(User.getName());
        topBarNameLabel.setFont(new Font("DIALOG", Font.ITALIC, 18));
        topBarNameLabel.setForeground(Theme.textColor);
        topBarNameLabel.setSize(300, 25);
        topBarProfileButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/profile_icon.png")));
        topBarProfileButton.setBorder(null);
        topBarProfileButton.setSize(25, 25);
        topBarProfileButton.setBackground(null);
        topBarProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                removeThisPanel();
                Main.window.add(new ProfilePanel());
            }
        });
        topBarProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        topBarLogoutButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/logout_icon.png")));
        topBarLogoutButton.setSize(25, 25);
        topBarLogoutButton.setBackground(null);
        topBarLogoutButton.setBorder(null);
        topBarLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                removeThisPanel();
                Main.window.add(new SignInPanel());
            }
        });
        topBarLogoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        topBarProfileButton.setLocation(10, 3);
        topBar.add(topBarProfileButton);
        topBarNameLabel.setLocation(45, 3);
        topBar.add(topBarNameLabel);
        topBarLogoutButton.setLocation(Main.window.getWidth() - 50, 3);
        topBar.add(topBarLogoutButton);

        // TODO: CENTER PANEL
        centerPanel = new JPanel();
        centerPanel.setBackground(Theme.defaultBackground);
        postsListPanel = new JPanel();
        postsListPanel.setBackground(Theme.defaultBackground);
        postsListPanel.setPreferredSize(new Dimension(360, 800));

        postListScrollPane = new JScrollPane();
        postListScrollPane.setBackground(Theme.defaultBackground);
        postListScrollPane.setViewportView(postsListPanel);
        postListScrollPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0, 52)), "Posts", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        postListScrollPane.setHorizontalScrollBar(null);
        postListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        postListScrollPane.getVerticalScrollBar().setEnabled(false);
        postListScrollPane.setPreferredSize(new Dimension(390, 500));
        postListScrollPane.setVisible(true);

        // CREATE POST STUFF
        JPanel createPostPanel = new JPanel();
        createPostPanel.setPreferredSize(new Dimension(350, 80));
        JTextArea postContentArea = new JTextArea();
        postContentArea.setBorder(new TitledBorder("Create Post"));

        createPostPanel.add(postContentArea);

        //centerPanel.add(createPostPanel);
        centerPanel.add(postListScrollPane);

        // TODO: ADD EXISTING POSTS
        for (String[] post : PostManager.posts) {
            addExistingPost(post[0], post[1], post[2], post[3]);
        }

        // TODO: RIGHT PANEL
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Theme.defaultBackground);
        gbc.insets = new Insets(5,5,5,5);
        rightPanel.setBorder(new MatteBorder(0, 1, 0, 0, Theme.defaultForeground));
        rightPanel.setPreferredSize(new Dimension(250, 600));
        JTextArea postContentTextArea = new JTextArea(5, 20);
        postContentTextArea.setBackground(Theme.textAreaBackground);
        postContentTextArea.setText("Create a post");
        postContentTextArea.setForeground(Color.gray);
        postContentTextArea.setLineWrap(true);
        postContentTextArea.setMargin(new Insets(5,5,5,5));
        postContentTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (postContentTextArea.getText().equals("Create a post")) {
                    postContentTextArea.setText("");
                    postContentTextArea.setForeground(Theme.textColor);
                    postContentTextArea.setCaretColor(Theme.textColor);
                }
            }
        });

        JButton postButton = new JButton("Post");
        postButton.setFocusable(false);
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (postContentTextArea.getText().length() > 150) {
                    JOptionPane.showMessageDialog(null, "That post is too long");
                } else {
                    String postID = generatePostID();
                    addPost(postContentTextArea.getText(), postID);
                    PostManager.addPost(postContentTextArea.getText(), User.getName(), getTime(), postID);
                    postContentTextArea.setText("Create a post");
                    postContentTextArea.setForeground(Color.gray);
                    postContentTextArea.setCaretColor(new Color(0, 0, 0, 1));
                    PostManager.savePosts();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(postContentTextArea, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(postButton, gbc);


        // TODO: ADD INTERNAL PANELS
        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    public void addPost(String content, String id) {
        postCount++;
        postsListPanel.setPreferredSize(new Dimension(360, postCount * 100));
        postsListPanel.revalidate();
        postsListPanel.repaint();
        // CREATE PANEL FOR POST
        JPanel newPost = new JPanel(null);
        newPost.setPreferredSize(new Dimension(350, 95));
        newPost.setBackground(Theme.defaultForeground);
        newPost.setBorder(new LineBorder(Color.GRAY, 1));

        // POST ID
        JLabel postID = new JLabel(id);
        System.out.println("POST ID: " + postID.getText());

        // CREATE & EDIT COMPONENTS
        JLabel posterNameLabel = new JLabel(User.getName());
        posterNameLabel.setFont(new Font("DIALOG", Font.BOLD, 14));
        posterNameLabel.setForeground(Theme.textColor);
        //posterNameLabel.setSize(325, 25);
        posterNameLabel.setSize(posterNameLabel.getPreferredSize());
        posterNameLabel.setLocation(5,0);

        JLabel posterAtLabel = new JLabel("@" + Utilities.removeSpaces(User.getName()));
        posterAtLabel.setFont(atLabelFont);
        posterAtLabel.setFont(posterAtLabel.getFont().deriveFont(Font.PLAIN, 13));
        posterAtLabel.setForeground(Color.GRAY);
        posterAtLabel.setSize(325, 20);
        //posterAtLabel.setLocation(125,0);
        posterAtLabel.setLocation(posterNameLabel.getWidth() + 10, 0);

        content = revampContent(content)[0];
        JLabel contentLabel = new JLabel(content);
        //System.out.println(contentLabel.getText().length());
        contentLabel.setBackground(new Color(0, 0, 0, 1));
        contentLabel.setForeground(Theme.textColor);
        contentLabel.setSize(325, 50);
        contentLabel.setLocation(10, 5 + (Integer.parseInt(revampContent(content)[1]) * 12));
        contentLabel.setFont(new Font("DIALOG", Font.PLAIN, 12));

        JButton removeButton = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon.png")));
        removeButton.setSize(25, 25);
        removeButton.setBackground(null);
        removeButton.setBorder(new MatteBorder(1, 0, 0, 1, Color.GRAY));
        removeButton.setLocation(325, 0);

        JLabel timeLabel = new JLabel(getTime());
        timeLabel.setForeground(Theme.textColor);
        timeLabel.setSize(120, 25);
        timeLabel.setLocation(240, 70);

        // ADD COMPONENTS TO POST
        newPost.add(posterNameLabel);
        newPost.add(posterAtLabel);
        newPost.add(contentLabel);
        if (posterNameLabel.getText().equals(User.getName()) || User.getName().equals("Admin")) {
            newPost.add(removeButton);
        }
        newPost.add(timeLabel);

        newPost.add(postID);

        // ADD THE POST
        postsListPanel.add(newPost);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                postsListPanel.remove(newPost);
                postCount--;
                revalidate();
                repaint();
                PostManager.removePost(id);
                PostManager.savePosts();
            }
        });
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon2.png")));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon.png")));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public String getTime() {
        String time = new SimpleDateFormat("MMddyyyyHHmm").format(Calendar.getInstance().getTime());
        String monthString = time.substring(0,2);
        String dayString = time.substring(2,4);
        String year = time.substring(4,8);
        String hourString = time.substring(8,10);
        String minute = time.substring(10,12);

        String meridian = "";

        // EDIT DAY
        if (dayString.charAt(0) == '0') {
            dayString = Character.toString(dayString.charAt(1));
        } int day = Integer.parseInt(dayString);

        // EDIT MONTH
        if (monthString.charAt(0) == '0') {
            monthString = Character.toString(monthString.charAt(1));
        } int month = Integer.parseInt(monthString);

        // EDIT HOUR
        int hour = Integer.parseInt( time.substring(8,10) );
        if (hour > 11 && hour < 24) {
            meridian = "pm";
        } else {
            meridian = "am";
        }
        if (hour > 12) {
            hour -= 12;
        }

        return month + "/" + day + "/" + year + " " + hour + ":" + minute + meridian;
    }

    public void removeThisPanel() {
        Main.removePanel(this);
    }

    public String[] revampContent(String content) {

        String newString = "<html>";

        int j = 0;
        int lines = 1;
        for (int i = 0; i < content.length(); i++) {
            newString += content.charAt(i);
            if (j > 55 && content.charAt(i + 1) == (' ')) {
                newString += "<br>";
                lines++;
                j = 0;
            }
        }
        newString += "</html>";

        return new String[] {newString, "" + lines};
    }

    public void addExistingPost(String content, String poster, String time, String id) {
        postCount++;
        postsListPanel.setPreferredSize(new Dimension(360, postCount * 100));
        postsListPanel.revalidate();
        postsListPanel.repaint();
        // CREATE PANEL FOR POST
        JPanel newPost = new JPanel(null);
        newPost.setPreferredSize(new Dimension(350, 95));
        newPost.setBackground(Theme.defaultForeground);
        newPost.setBorder(new LineBorder(Color.GRAY, 1));

        // POST ID
        JLabel postID = new JLabel(id);

        // CREATE & EDIT COMPONENTS
        JLabel posterNameLabel = new JLabel(poster);
        posterNameLabel.setFont(new Font("DIALOG", Font.BOLD, 14));
        posterNameLabel.setForeground(Theme.textColor);
        //posterNameLabel.setSize(325, 25);
        posterNameLabel.setSize(posterNameLabel.getPreferredSize());
        posterNameLabel.setLocation(5,0);

        JLabel posterAtLabel = new JLabel("@" + Utilities.removeSpaces(poster));
        posterAtLabel.setFont(atLabelFont);
        posterAtLabel.setFont(posterAtLabel.getFont().deriveFont(Font.PLAIN, 13));
        posterAtLabel.setForeground(Color.gray);
        posterAtLabel.setSize(325, 20);
        //posterAtLabel.setLocation(125,0);
        posterAtLabel.setLocation(posterNameLabel.getWidth() + 10, 0);

        content = revampContent(content)[0];
        JLabel contentLabel = new JLabel(content);
        System.out.println(contentLabel.getText().length());
        contentLabel.setBackground(new Color(0, 0, 0, 1));
        contentLabel.setForeground(Theme.textColor);
        contentLabel.setSize(325, 50);
        contentLabel.setLocation(10, 5 + (Integer.parseInt(revampContent(content)[1]) * 12));
        contentLabel.setFont(new Font("DIALOG", Font.PLAIN, 12));

        JButton removeButton = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon.png")));
        removeButton.setSize(25, 25);
        removeButton.setBackground(null);
        removeButton.setBorder(new MatteBorder(1, 0, 0, 1, Color.GRAY));
        removeButton.setLocation(325, 0);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setForeground(Theme.textColor);
        timeLabel.setSize(120, 25);
        timeLabel.setLocation(240, 70);

        // ADD COMPONENTS TO POST
        newPost.add(posterNameLabel);
        newPost.add(posterAtLabel);
        newPost.add(contentLabel);
        if (posterNameLabel.getText().equals(User.getName()) || User.getName().equals("Admin")) {
            newPost.add(removeButton);
        }
        newPost.add(timeLabel);

        // ADD THE POST
        postsListPanel.add(newPost);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                postsListPanel.remove(newPost);
                postCount--;
                revalidate();
                repaint();
                PostManager.removePost(id);
                PostManager.savePosts();
            }
        });
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon2.png")));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/remove_post_icon.png")));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public String generatePostID() {
        String id;

        long rand = (long) (Math.random()*Long.MAX_VALUE);

        id = "" + rand;

        return id;
    }

}
