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