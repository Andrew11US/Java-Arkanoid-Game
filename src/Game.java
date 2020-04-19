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
    public static Game game = new Game();

    private Game() {
        initMenu();
    }
    // MARK: Initializes menu with action listeners
    private void initMenu() {
        String name = new String("Arkanoid v1.0.2");
        JFrame menuFrame = new JFrame(name);
        menuFrame.getContentPane().setBackground(Color.BLACK);
        menuFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        BlackLabel nameLbl = new BlackLabel(name);
        BlackButton startBtn = new BlackButton("Start", Color.WHITE);
        BlackButton scoreBtn = new BlackButton("Scoreboard", Color.WHITE);
        BlackButton exitBtn = new BlackButton("Exit", Color.RED);

        // Using GridLayout to compound multiple buttons on one JFrame
        menuFrame.add(nameLbl);
        menuFrame.add(startBtn);
        menuFrame.add(scoreBtn);
        menuFrame.add(exitBtn);
        menuFrame.setLayout(new GridLayout(5,1));
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);
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

    private void newGame() {
        JFrame frame = new JFrame("Arkanoid by Andrii Halabuda");
        GamePanel gamePanel = new GamePanel();
        frame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.setVisible(true);
    }

    private void showScoreboard() {
        JFrame scoreFrame = new JFrame("Scoreboard");
        scoreFrame.getContentPane().setBackground(Color.BLACK);
        scoreFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        ArrayList<String> scores = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get("scores.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> scores.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sorting descending by Score
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
        Collections.reverse(scores);

        // Declarations and assignments
        BlackLabel titleLbl = new BlackLabel("Scoreboard");
        BlackTextArea textArea = new BlackTextArea(20,3);
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        JButton close = new JButton("Close");

        scrollableTextArea.getViewport().setOpaque(false);
        scrollableTextArea.setOpaque(false);
        scrollableTextArea.getVerticalScrollBar().setPreferredSize (new Dimension(0,0));
        scrollableTextArea.getHorizontalScrollBar().setPreferredSize (new Dimension(0,0));

        scores.forEach(item-> textArea.append(item + System.lineSeparator()));
        scoreFrame.getContentPane().add(titleLbl, BorderLayout.NORTH);
        scoreFrame.getContentPane().add(scrollableTextArea, BorderLayout.CENTER);
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        close.setSize(Const.WINDOW_WIDTH,30);
        close.addActionListener(exit->{
            scoreFrame.setVisible(false);
            scoreFrame.removeAll();
        });
        scoreFrame.add(close, BorderLayout.SOUTH);
        scoreFrame.setVisible(true);
    }

    // MARK: Gets score and writes it to scores.txt tile, creates file if it doesn't exist
    public void gameOver(int score) {
        String str = "GAME OVER!\nYour Score: " + score + "\n Type your name: ";
        String name = JOptionPane.showInputDialog(str);
        String scoreStr;

        if (!name.trim().isEmpty()) {
            scoreStr = score + " " + name + System.lineSeparator();
        } else {
            scoreStr = score + " " + "Player X" + System.lineSeparator();
        }
        try {
            Files.write(Paths.get("scores.txt"), scoreStr.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("scores.txt"));
                fileOutputStream.write(scoreStr.getBytes());
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }

        initMenu();
    }

    // MARK: Returns Game singleton object
    public static Game getInstance() {
        return game;
    }
}
