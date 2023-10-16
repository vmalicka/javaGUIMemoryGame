import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


public class MemoryGameGUI extends JFrame {
    private ArrayList<String> imagePaths;
    private ArrayList<String> cardImages;
    private JButton[] cardButtons;
    private int numberOfMatches;
    private int firstCardIndex;
    private int secondCardIndex;
    private int moves;
    private Timer timer;
    private int gridSize;


    public MemoryGameGUI(int gridSize) {
        this.gridSize = gridSize;
        setTitle("Picture Memory Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        imagePaths = new ArrayList<>();
        cardImages = new ArrayList<>();
        numberOfMatches = 0;
        firstCardIndex = -1;
        secondCardIndex = -1;
        moves = 0;


        initializeImagePaths();
        initializeCardImages();


        JPanel cardPanel = new JPanel(new GridLayout(6, 6));
        cardButtons = new JButton[12];


        for (int i = 0; i < cardButtons.length; i++) {
            final int index = i;


            cardButtons[i] = new JButton();
            cardButtons[i].setIcon(new ImageIcon("Cardback.png"));
            cardButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { handleCardClick(index); }
            });
            cardPanel.add(cardButtons[i]);
        }


        add(cardPanel);
    }


    private void initializeImagePaths() {
        imagePaths.add("Rose.png");
        imagePaths.add("Lily.png");
        imagePaths.add("Daisy.png");
        imagePaths.add("Orchid.png");
        imagePaths.add("Sunflower.png");
        imagePaths.add("Carnation.png");

        imagePaths.add("Rose.png");
        imagePaths.add("Lily.png");
        imagePaths.add("Daisy.png");
        imagePaths.add("Orchid.png");
        imagePaths.add("Sunflower.png");
        imagePaths.add("Carnation.png");




        // Shuffles image paths
        Collections.shuffle(imagePaths);
        Collections.shuffle(cardImages);
    }
    private void initializeCardImages() {


        // Initialize cardImages
        for (int i = 0; i < gridSize; i++) {
            cardImages.add("");
        }
        Collections.shuffle(cardImages);
    }
    private void handleCardClick(int index) {
        if (cardButtons[index].getIcon() == null) {
            return; // Already matched card, do nothing
        }


        if (firstCardIndex == -1) {
            firstCardIndex = index;
            cardButtons[firstCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));
        } else {
            secondCardIndex = index;
            cardButtons[secondCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));


            // Increment the moves
            moves++;


            timer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Hide the cards after 5 seconds
                    cardButtons[firstCardIndex].setIcon(new ImageIcon("Cardback.png"));
                    cardButtons[secondCardIndex].setIcon(new ImageIcon("Cardback.png"));
                    firstCardIndex = -1;
                    secondCardIndex = -1;
                }
            });
            timer.setRepeats(false);
            timer.start();


            if (imagePaths.get(firstCardIndex).equals(imagePaths.get(secondCardIndex))) {
                cardButtons[firstCardIndex].setIcon(new ImageIcon("Smile.png"));
                cardButtons[secondCardIndex].setIcon(new ImageIcon("Smile.png"));
                cardImages.set(firstCardIndex, null);
                cardImages.set(secondCardIndex, null);
                numberOfMatches++;


                if (numberOfMatches == gridSize / 2) {
                    JOptionPane.showMessageDialog(null, "Congratulations!" + moves + "Goodbye");
                    resetGame();
                }
            }
        }
    }
    private void resetGame() {
        // Reset the game by creating a new MemoryGame instance
        new MemoryGameGUI(gridSize).setVisible(true);
        dispose();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Prompt the user for the grid size (12 cards or 24 cards)
                String gridSizeInput = JOptionPane.showInputDialog("Enter the grid size (12 or 24):");
                int gridSize = Integer.parseInt(gridSizeInput);


                if (gridSize != 12 && gridSize != 24) {
                    JOptionPane.showMessageDialog(null, "Invalid grid size. Please enter 12 or 24.");
                    return;
                }


                new MemoryGameGUI(gridSize).setVisible(true);
            }
        });
    }
}

