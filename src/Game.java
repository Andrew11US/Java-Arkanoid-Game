import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public JFrame menuFrame;
    public JFrame frame;
    public JFrame scoreFrame;
//    private JLabel scoreLbl;
    public GamePanel gamePanel;
    private ArrayList<String> scores = new ArrayList<>();
    private Thread thread;
    private boolean isRunning = false;

    private Game() {
        // Initializations
        frame = new JFrame("Arkanoid v0.9");
        scoreFrame = new JFrame("Scoreboard");
        gamePanel = new GamePanel();
        menuFrame = new JFrame();
        JLabel nameLbl = new JLabel("Arkanoid v0.9", SwingConstants.CENTER);
        JButton startBtn = new JButton("Start");
        JButton scoreBtn = new JButton("Scoreboard");
        JButton exitBtn = new JButton("Exit");

        frame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Using GridLayout to compound multiple buttons on one JFrame
        menuFrame.add(nameLbl);
        menuFrame.add(startBtn);
        menuFrame.add(scoreBtn);
        menuFrame.add(exitBtn);
        menuFrame.setLayout(new GridLayout(5,1));
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        menuFrame.setResizable(false);
        menuFrame.setVisible(true);

        scoreFrame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);

        // MARK: Button Action Listeners
        startBtn.addActionListener(listener -> {
            gamePanel = null;
//            frame.remove(gamePanel);
            gamePanel = new GamePanel();
            menuFrame.setVisible(false);
            frame.add(gamePanel);
            frame.setVisible(true);
        });

        // TODO: Refactor to separate function
        scoreBtn.addActionListener(listener -> {
            try (Stream<String> lines = Files.lines(Paths.get("scores.txt"), Charset.defaultCharset())) {
                lines.forEachOrdered(line -> scores.add(line));
            } catch (IOException e) {
                // Error occurred reading the "scores.txt"
                e.printStackTrace();
            }
            scores.sort(new Comparator<String>() { // Ascending order
                @Override
                public int compare(String o1, String o2) {
                    int space1 = o1.lastIndexOf(" ");
                    int space2 = o2.lastIndexOf(" ");
                    return o1.substring(o1.length()-(o1.length()-space1)).compareTo(
                            o2.substring(o2.length()-(o2.length()-space2)));
                }
            });
            Collections.reverse(scores); // Make descending order

            JLabel titleLbl = new JLabel("Scoreboard", SwingConstants.CENTER);
            titleLbl.setFont(new Font("Calibri",Font.BOLD,32));
            JTextArea textArea = new JTextArea(20,3);
            textArea.setEnabled(false);
//            textArea.setSize(Const.WINDOW_WIDTH,Const.WINDOW_HEIGHT - 30);
            scores.forEach(item->{
//                System.out.println(item);
//                scoreFrame.add(new ScoreLabel(item));
                textArea.append(item+System.lineSeparator());
            });
            JScrollPane scrollableTextArea = new JScrollPane(textArea);
//            scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//            scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scoreFrame.getContentPane().add(titleLbl, BorderLayout.NORTH);
            scoreFrame.getContentPane().add(scrollableTextArea, BorderLayout.CENTER);
            scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton close = new JButton("Close");
            close.setSize(Const.WINDOW_WIDTH,30);
            close.addActionListener(exit->{
                scoreFrame.setVisible(false);
                menuFrame.setVisible(true);
                scoreFrame.removeAll();
            });
            scoreFrame.add(close, BorderLayout.SOUTH);
//            scoreFrame.setLayout(new GridLayout(11,1));
            scoreFrame.setVisible(true);
            menuFrame.setVisible(false);
//            frame.add(scoreFrame);
//            frame.setVisible(true);
        });

        exitBtn.addActionListener(listener -> {
            System.exit(0);
        });

//        frame.add(gamePanel);


//        JFrame frame = new JFrame("Border Layout");
//        JButton button,button1, button2, button3,button4;
//        button = new JButton("left");
//        button1 = new JButton("right");
//        button2 = new JButton("top");
//        button3 = new JButton("bottom");
//        button4 = new JButton("center");
//        frame.add(button,BorderLayout.WEST);
//        frame.add(button1, BorderLayout.EAST);
//        frame.add(button2, BorderLayout.NORTH);
//        frame.add(button3, BorderLayout.SOUTH);
//        frame.add(button4, BorderLayout.CENTER);
//
//        frame.setSize(300,300);
//        frame.setVisible(true);

//        frame.setVisible(true);
    }

    private void run() {
//        thread = new Thread(()->{
//            while (true) {
//                gamePanel.update();
//
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
    }

    public void gameOver(int score) {
        String str = "GAME OVER!\nYour Score: " + score + "\n Type your name: ";
        String name = JOptionPane.showInputDialog(str);

        if (name != null) {
            String write = name + " " + score + System.lineSeparator();

            try {
                Files.write(Paths.get("scores.txt"), write.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                try {
                    FileOutputStream f = new FileOutputStream(new File("scores.txt"));
                    f.write(write.getBytes());
                    f.close();
                } catch (IOException e1) {
                }

            }
        }
    }
    /**
     * @return Game() singleton object
     */
    public static Game getInstance() {
        return game;
    }
}
