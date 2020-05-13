import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class Game {
    // Creating shared instance
    private static Game game = new Game();
    private JFrame menuFrame;
    private JFrame gameFrame;
    private JFrame scoreFrame;

    // Game constructor
    private Game() {
        initMenu();
    }
    // MARK: Initializes menu with UI elements and adding action listeners
    private void initMenu() {
        String name = "Arkanoid v1.0.8";
        menuFrame = new JFrame(name);
        menuFrame.getContentPane().setBackground(Color.BLACK);
        menuFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);

        // Declaring and initializing UI Controls
        BlackLabel nameLbl = new BlackLabel(name);
        BlackButton startBtn = new BlackButton("Start", Color.WHITE);
        BlackButton scoreBtn = new BlackButton("Scoreboard", Color.WHITE);
        BlackButton exitBtn = new BlackButton("Exit", Color.RED);

        // Using GridLayout to compound UI elements on one JFrame
        menuFrame.add(nameLbl);
        menuFrame.add(startBtn);
        menuFrame.add(scoreBtn);
        menuFrame.add(exitBtn);
        menuFrame.setLayout(new GridLayout(5,1));
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

        // MARK: Menu Button Action Listeners
        startBtn.addActionListener(listener -> {
            newGame();
        });

        scoreBtn.addActionListener(listener -> {
            showScoreboard();
        });

        exitBtn.addActionListener(listener -> {
            System.exit(0);
        });
    }

    // Compount Game UI using gamePanel as main view to display game elements
    private void newGame() {
        gameFrame = new JFrame("Arkanoid by Andrii Halabuda");
        GamePanel gamePanel = new GamePanel();
        gameFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(gamePanel);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        menuFrame.setVisible(false);
        menuFrame = null;
    }

    // Create Scoreboard UI and populate it with appropriate data from persistent storage
    private void showScoreboard() {
        scoreFrame = new JFrame("Scoreboard");
        scoreFrame.getContentPane().setBackground(Color.BLACK);
        scoreFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        // Scores list to be read, sorted and populated to the TextArea
        ArrayList<String> scores = new ArrayList<>();
        // Parsing "scores.txt" if exists, indicates error message instead
        try (Stream<String> lines = Files.lines(Paths.get("scores.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> scores.add(line));
        } catch (IOException e) { scores.add("Nothing to Show"); }

        // Sort descending by Score using custom comparator
        // Getting space indices, parsing int value before player name in the file
        // Sorting with comparator
        scores.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                int space1 = str1.indexOf(" ");
                int space2 = str2.indexOf(" ");
                Integer a = Integer.parseInt(str1.substring(0,space1));
                Integer b = Integer.parseInt(str2.substring(0,space2));
                return a.compareTo(b);
            }
        });
        // Make descending order
        Collections.reverse(scores);

        // Compound View and embedding TextArea into ScrollPane
        BlackLabel titleLbl = new BlackLabel("Scoreboard");
        BlackTextArea textArea = new BlackTextArea(20,3);
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        JButton close = new JButton("Close");
        // UI Controls setup
        scrollableTextArea.getViewport().setOpaque(false);
        scrollableTextArea.setOpaque(false);
        scrollableTextArea.getVerticalScrollBar().setPreferredSize (new Dimension(0,0));
        scrollableTextArea.getHorizontalScrollBar().setPreferredSize (new Dimension(0,0));
        // Populating TextArea with scores data
        scores.forEach(item-> textArea.append(item + System.lineSeparator()));
        scoreFrame.getContentPane().add(titleLbl, BorderLayout.NORTH);
        scoreFrame.getContentPane().add(scrollableTextArea, BorderLayout.CENTER);
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setting close button and it listener
        close.setSize(Const.WINDOW_WIDTH,30);
        close.addActionListener(exit->{
            initMenu();
            scoreFrame.setVisible(false);
            scoreFrame = null;
        });
        scoreFrame.add(close, BorderLayout.SOUTH);
        scoreFrame.setLocationRelativeTo(null);
        scoreFrame.setVisible(true);
        menuFrame.setVisible(false);
        menuFrame = null;
    }

    // MARK: Game Over method
    public void endGame(int score) {
        // Compound and show game over message dialog using option pane
        String str = "GAME OVER!\nYour Score: " + score + "\n Type your name: ";
        String name = JOptionPane.showInputDialog(str);
        String scoreStr;
        // Getting user name falling back to default name in case of empty
        if (!name.trim().isEmpty()) {
            scoreStr = score + " " + name + System.lineSeparator();
        } else {
            scoreStr = score + " " + "Player X" + System.lineSeparator();
        }
        try {
            // Trying to parse scores file
            Files.write(Paths.get("scores.txt"), scoreStr.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            try {
                // File could not be parsed, does not exist yet, creating the new one
                FileOutputStream fileOutputStream = new FileOutputStream(new File("scores.txt"));
                fileOutputStream.write(scoreStr.getBytes());
                fileOutputStream.close();
            } catch (IOException ex) {
                // Throwing an exception when file could not be created for the first time
                ex.getStackTrace();
            }
        }
        // Back to menu after game is over
        initMenu();
        // Invalidate gameFrame
        gameFrame.setVisible(false);
        gameFrame = null;
    }

    // MARK: Returns Game singleton object
    public static Game getInstance() { return game; }
}
