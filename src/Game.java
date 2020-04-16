import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    public static Game game = new Game();
    private JFrame menuFrame;
    private JFrame frame;
//    private JLabel scoreLbl;
    private GamePanel gamePanel;
    private Thread thread;
    private boolean isRunning = false;

    private Game() {
        // Initializations
        frame = new JFrame("Arkanoid v0.4");
        gamePanel = new GamePanel();
        menuFrame = new JFrame();
        JLabel nameLbl = new JLabel("Arkanoid v0.5", SwingConstants.CENTER);
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

        // MARK: Button Action Listeners
        startBtn.addActionListener(listener -> {
            menuFrame.setVisible(false);
            frame.setVisible(true);
        });

        exitBtn.addActionListener(listener -> {
            System.exit(0);
        });

        frame.add(gamePanel);


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

    /**
     * @return Game() singleton object
     */
    public static Game getInstance() {
        return game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enter");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
