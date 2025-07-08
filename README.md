void restartGame() {
        playerOne.clear();
        playerTwo.clear();
        isPlayerOneTurn = true;
        turnLabel.setText(playerOneName + "'s Turn");
        turnLabel.setForeground(new Color(0, 102, 204));
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
    }
}

// Helper Class for text input limitation
class JTextFieldLimit extends javax.swing.text.PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
# GameProject
