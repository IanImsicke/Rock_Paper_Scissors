import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Rock Paper Scissors Game GUI.
 */
public class RockPaperScissorsFrame extends JFrame {
    private JPanel buttonPanel;
    private JPanel statsPanel;
    private JTextArea resultsTextArea;
    private int playerWins;
    private int computerWins;
    private int ties;

    /**
     * Constructor to initialize the frame.
     */
    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        buttonPanel = createButtonPanel();
        statsPanel = createStatsPanel();
        resultsTextArea = createResultsTextArea();

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultsTextArea), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the frame

        // Show the frame
        setVisible(true);
    }

    /**
     * Creates the panel with buttons for Rock, Paper, Scissors, and Quit.
     *
     * @return The button panel.
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Game Options"));

        // Create buttons with icons
        JButton rockButton = createButton("Rock", "rock.jpg");
        JButton paperButton = createButton("Paper", "paper.jpg");
        JButton scissorsButton = createButton("Scissors", "scissors.jpg");
        JButton quitButton = createButton("Quit", "end game.png");

        // Add action listeners
        rockButton.addActionListener(new GameButtonListener("Rock"));
        paperButton.addActionListener(new GameButtonListener("Paper"));
        scissorsButton.addActionListener(new GameButtonListener("Scissors"));
        quitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        panel.add(rockButton);
        panel.add(paperButton);
        panel.add(scissorsButton);
        panel.add(quitButton);

        return panel;
    }

    /**
     * Creates a button with the specified label and icon.
     *
     * @param label The label of the button.
     * @param icon  The filename of the icon (null if no icon).
     * @return The created button.
     */
    private JButton createButton(String label, String icon) {
        JButton button = new JButton(label);
        if (icon != null) {
            button.setIcon(new ImageIcon("src/" + icon));
        }
        return button;
    }

    /**
     * Creates the panel with statistics for player wins, computer wins, and ties.
     *
     * @return The stats panel.
     */
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Game Stats"));

        JLabel playerWinsLabel = new JLabel("Player Wins:");
        JTextField playerWinsField = createStatField();
        JLabel computerWinsLabel = new JLabel("Computer Wins:");
        JTextField computerWinsField = createStatField();
        JLabel tiesLabel = new JLabel("Ties:");
        JTextField tiesField = createStatField();

        // Add components to the stats panel
        panel.add(playerWinsLabel);
        panel.add(playerWinsField);
        panel.add(computerWinsLabel);
        panel.add(computerWinsField);
        panel.add(tiesLabel);
        panel.add(tiesField);

        return panel;
    }

    /**
     * Creates a text field for displaying statistics.
     *
     * @return The created text field.
     */
    private JTextField createStatField() {
        JTextField textField = new JTextField(5);
        textField.setEditable(false);
        return textField;
    }

    /**
     * Creates the text area for displaying game results.
     *
     * @return The created text area.
     */
    private JTextArea createResultsTextArea() {
        JTextArea textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        return textArea;
    }

    /**
     * Listener for game buttons.
     */
    private class GameButtonListener implements ActionListener {
        private String playerChoice;

        /**
         * Constructor for GameButtonListener.
         *
         * @param choice The player's choice.
         */
        public GameButtonListener(String choice) {
            playerChoice = choice;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Computer randomly selects Rock, Paper, or Scissors
            String[] options = {"Rock", "Paper", "Scissors"};
            Random random = new Random();
            int index = random.nextInt(options.length);
            String computerChoice = options[index];

            // Determine the winner
            String result = determineWinner(playerChoice, computerChoice);

            // Update statistics and display results
            updateStats(result);
            updateResultsTextArea(result);
        }

        /**
         * Determines the winner of the game.
         *
         * @param playerChoice   The player's choice.
         * @param computerChoice The computer's choice.
         * @return The result of the game.
         */
        private String determineWinner(String playerChoice, String computerChoice) {
            if (playerChoice.equals(computerChoice)) {
                return "Tie";
            } else if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                    (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                    (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
                return "Player Wins";
            } else {
                return "Computer Wins";
            }
        }

        /**
         * Updates the statistics based on the game result.
         *
         * @param result The result of the game.
         */
        private void updateStats(String result) {
            if (result.equals("Player Wins")) {
                playerWins++;
            } else if (result.equals("Computer Wins")) {
                computerWins++;
            } else {
                ties++;
            }

            // Update the text fields in the stats panel
            ((JTextField) statsPanel.getComponent(1)).setText(String.valueOf(playerWins));
            ((JTextField) statsPanel.getComponent(3)).setText(String.valueOf(computerWins));
            ((JTextField) statsPanel.getComponent(5)).setText(String.valueOf(ties));
        }

        /**
         * Updates the results text area with the latest game result.
         *
         * @param result The result of the game.
         */
        private void updateResultsTextArea(String result) {
            resultsTextArea.append(result + "\n");
        }
    }

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RockPaperScissorsFrame::new);
    }
}