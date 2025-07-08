import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.*;
import javax.swing.text.BadLocationException;

public class XandO extends JFrame {

    ArrayList<Integer> playerOne = new ArrayList<>();
    ArrayList<Integer> playerTwo = new ArrayList<>();
    boolean isPlayerOneTurn = true;
    String playerOneName = "Player 1";
    String playerTwoName = "Player 2";
    int playerOneScore = 0;
    int playerTwoScore = 0;

    JButton[] buttons = new JButton[9];
    JPanel myPanel = new JPanel();
    JLabel turnLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JButton restartButton = new JButton("Restart Game");
    Clip clickSound;

    public XandO() {
        setTitle("🎮 X and O Game");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadSound();             // Sound system
        createWelcomeScreen();   // Welcome screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new XandO());
    }
        private void loadSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("click.wav"));
            clickSound = AudioSystem.getClip();
            clickSound.open(audioInputStream);
        } catch (Exception e) {
            System.out.println("Sound file not found or cannot be played.");
        }
    }

    private void playClickSound() {
        if (clickSound != null) {
            if (clickSound.isRunning()) clickSound.stop();
            clickSound.setFramePosition(0);
            clickSound.start();
        }
    }

    private void createWelcomeScreen() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(6, 1));
        welcomePanel.setBackground(new Color(240, 248, 255)); // Soft background

        JLabel title = new JLabel("🎉 Welcome to X and O Game 🎯", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        title.setForeground(new Color(199, 21, 133));

        JTextField playerOneField = new JTextField();
        playerOneField.setDocument(new JTextFieldLimit(16));
        playerOneField.setToolTipText("Enter Player 1 Name");

        JTextField playerTwoField = new JTextField();
        playerTwoField.setDocument(new JTextFieldLimit(16));
        playerTwoField.setToolTipText("Enter Player 2 Name");

        JButton startButton = new JButton("Continue");
        startButton.setBackground(new Color(218, 112, 214));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel tipLabel = new JLabel("👥 Enter your names to begin!", SwingConstants.CENTER);
        tipLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        tipLabel.setForeground(Color.DARK_GRAY);

        welcomePanel.add(title);
        welcomePanel.add(playerOneField);
        welcomePanel.add(playerTwoField);
        welcomePanel.add(tipLabel);
        welcomePanel.add(startButton);

        add(welcomePanel, BorderLayout.CENTER);
        setVisible(true);

        startButton.addActionListener(e -> {
            playerOneName = playerOneField.getText().isEmpty() ? "Player 1" : playerOneField.getText();
            playerTwoName = playerTwoField.getText().isEmpty() ? "Player 2" : playerTwoField.getText();
            remove(welcomePanel);
            drawGrid();
            revalidate();
            repaint();
        });
    }
     void drawGrid() {
        myPanel.setLayout(new GridLayout(3, 3));
        myPanel.setBackground(new Color(255, 239, 213));

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
            button.setBackground(new Color(255, 182, 193));
            final int index = i + 1;
            button.addActionListener(e -> {
                playClickSound();
                handleMove(button, index);
            });
            buttons[i] = button;
            myPanel.add(button);
        }

        turnLabel.setText(playerOneName + "'s Turn");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(new Color(0, 102, 204));

        scoreLabel.setText(getScoreText());
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(25, 25, 112));

        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setBackground(new Color(255, 105, 180));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setPreferredSize(new Dimension(200, 40));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(255, 240, 245));
        topPanel.add(turnLabel);
        topPanel.add(scoreLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(199, 21, 133));
        bottomPanel.add(restartButton);

        restartButton.addActionListener(e -> restartGame());

        add(topPanel, BorderLayout.NORTH);
        add(myPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    String getScoreText() {
        return "📊 Score → " + playerOneName + ": " + playerOneScore + " | " + playerTwoName + ": " + playerTwoScore;
    }