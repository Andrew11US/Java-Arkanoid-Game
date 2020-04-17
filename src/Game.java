import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Game {
    public static Game game = new Game();
    public JFrame menuFrame;
    public JFrame frame;
    public JFrame scoreFrame;
//    private JLabel scoreLbl;
    public GamePanel gamePanel;
    private Thread thread;
    private boolean isRunning = false;

    private Game() {
        // Initializations
        frame = new JFrame("Arkanoid v0.7");
        scoreFrame = new JFrame("Scoreboard");
        gamePanel = new GamePanel();
        menuFrame = new JFrame();
        JLabel nameLbl = new JLabel("Arkanoid v0.7", SwingConstants.CENTER);
        JButton startBtn = new JButton("Start");
        JButton scoreBtn = new JButton("Scoreboard");
        JButton exitBtn = new JButton("Exit");

        frame.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Using BorderLayout to compound multiple buttons on one JFrame
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

        // TODO: Create functional scoreboard
        scoreBtn.addActionListener(listener -> {
            scoreFrame.add(new ScoreLabel("HELLO"));
            scoreFrame.add(new ScoreLabel("HELLO1"));
            scoreFrame.add(new ScoreLabel("HELLO2"));
            JButton close = new JButton("Close");
            close.addActionListener(exit->{
                scoreFrame.setVisible(false);
                menuFrame.setVisible(true);
                scoreFrame.removeAll();
            });
            scoreFrame.add(close);
            scoreFrame.setLayout(new GridLayout(4,1));
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
        String str =
                "GAME OVER!\nYour Score: " + score + "\n Type your name: ";
        String name = JOptionPane.showInputDialog(str);

        if (name != null) {
            String write = name + " " + score + "\n";

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
