import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class MemoryGameGUI {
    private JFrame frame;
    private JPanel cardPanel;
    private List<Card> cards;
    private Card firstCard;
    private Card secondCard;
    private int moves;
    private Timer timer;
    private int timePlayed;

    public MemoryGameGUI(int gridSize) {
        frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(new GridLayout(gridSize / 4, gridSize / 4));
        frame.add(cardPanel, BorderLayout.CENTER);

        cards = new ArrayList<>();
        for (int i = 1; i <= gridSize / 2; i++) {
            cards.add(new Card(i, "flower" + i + "Rose.png"));
            cards.add(new Card(i, "flower" + i + "Lily.png"));
            cards.add(new Card(i, "flower" + i + "Tulips.png"));
            cards.add(new Card(i, "flower" + i + "Iris.png"));
            cards.add(new Card(i, "flower" + i + "Daisy.png"));
            cards.add(new Card(i, "flower" + i + "Orchid.png"));
            cards.add(new Card(i, "flower" + i + "Sun Flower.png"));

        }
        Collections.shuffle(cards);

        for (Card card : cards) {
            JButton button = new JButton(new ImageIcon(card.getImagePath()));
            button.addActionListener(new CardClickListener(card));
            cardPanel.add(button);
        }

        frame.pack();
        frame.setVisible(true);
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    private class Card {
        private final int value;
        private final String imagePath;
        private boolean flipped;
        private boolean matched;

        public Card(int value, String imagePath) {
            this.value = value;
            this.imagePath = imagePath;
            this.flipped = false;
            this.matched = false;
        }

        public int getValue() {
            return value;
        }

        public String getImagePath() {
            return imagePath;
        }

        public boolean isFlipped() {
            return flipped;
        }

        public void setFlipped(boolean flipped) {
            this.flipped = flipped;
        }

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }
    }

    private class CardClickListener implements ActionListener {
        private final Card card;

        public CardClickListener(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!card.isFlipped() && !card.isMatched()) {
                flipCard(card);
                if (firstCard == null) {
                    firstCard = card;
                } else {
                    secondCard = card;
                    checkMatch();
                }
            }
        }

        private void flipCard(Card card) {
            card.setFlipped(true);
            // Update GUI to show the flipped card
        }

        private void checkMatch() {
            if (firstCard.getValue() == secondCard.getValue()) {
                firstCard.setMatched(true);
                secondCard.setMatched(true);
                // Update GUI to show the matched cards
                resetCards();
                checkGameStatus();
            } else {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flipCard(firstCard);
                        flipCard(secondCard);
                        resetCards();
                    }
                }, 5000);
            }
            moves++;
            // Update GUI to show the number of moves
        }

        private void resetCards() {
            firstCard = null;
            secondCard = null;
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }

        private void checkGameStatus() {
            boolean allMatched = true;
            for (Card card : cards) {
                if (!card.isMatched()) {
                    allMatched = false;
                    break;
                }
            }
            if (allMatched) {
                JOptionPane.showMessageDialog(frame, "Congratulations! You have completed the game in " + moves + " moves.");
                resetGame();
            }
        }
    }

    private void resetGame() {
        frame.dispose();
        int gridSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the GUI matrix (12 or 24):"));
        SwingUtilities.invokeLater(() -> new MemoryGameGUI(gridSize));
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the GUI matrix (12 or 24):"));
        SwingUtilities.invokeLater(() -> new MemoryGameGUI(gridSize));
    }


}

