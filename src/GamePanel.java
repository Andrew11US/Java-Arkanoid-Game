import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, Paintable, Runnable {
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Image image;
    private Thread thread;
    private boolean isPaused = false; // Pauses the game, changing the ball.speed to 0, (recoverable action)
    private boolean isRunning = true; // Stops the game breaking while loop, not recoverable!
    private int bricksCount = 0;
    public int level = 1;
    public int lives = 3;
    public int levelScore = 0;
    public int score = 0;
    public JLabel lbl;


    public GamePanel() {
//        resetScene(true);
        newGame();
        addKeyListener(this);
        setFocusable(true);
//        run();
    }

    @Override
    public void paint(Graphics g) {
        this.ball.paint(g);
        this.paddle.paint(g);
        bricks.forEach(block -> block.paint(g));

        g.drawImage(image, 0, 0, this);
        g.drawString("Level Score: "+ levelScore,20,20);
        g.drawString("Score: "+ score,20,40);
        g.drawString("Lives: "+lives,440,20);
    }

    private void newGame() {
        ball = new Ball();
        paddle = new Paddle();

        for(int i = 1; i < 7; ++i) {
            for(int j = 1; j < 5; ++j) {
                bricks.add(new Brick(i*60,j*40));
                bricksCount++;
            }
        }
    }

    private void resetScene(boolean isNewLevel) {
        ball = new Ball();
        paddle = new Paddle();

        if (isNewLevel) {
            levelScore = 0;
            bricksCount = 0;
            //*
            for(int i = 1; i < 7; ++i) {
                for(int j = 1; j < 5; ++j) {
                    bricks.add(new Brick(i*60,j*40));
                    bricksCount++;
                }
            }
            /*/
            for(int i = 2; i < 3; ++i) {
                for(int j = 2; j < 3; ++j) {
                    bricks.add(new Brick(i*120,j*40));
                    bricksCount++;
                }
            }
            //*/
        }
    }

    public void updateView() {
        // MARK: Constantly updates ball position in certain direction and speed
        ball.x += ball.xDirection * ball.speed;
        ball.y += ball.yDirection * ball.speed;

        if (ball.xDirection == 0 || ball.yDirection == 0) {
            System.out.println("Vertical or perpendicular");
            ball.xDirection *= -1;
            ball.yDirection *= -1;
        }

        // MARK: Ball hits left or right edge
        if(ball.x < 0 || ball.x + ball.width > getWidth()) {
            ball.xDirection *= -1;
        }

        // MARK: Ball hits a top edge
        if(ball.y < 0) {
            ball.yDirection *= -1;
        }

        // MARK: Ball hits a paddle on top of the platform or on one of the sides
        if(ball.intersects(paddle)) {
            if (ball.x + ball.size < paddle.x + 10) {
                ball.xDirection *= -1;
//                ball.yDirection *= -1;
                System.out.println("Left side intersection");
            } else if (ball.x > paddle.x + paddle.width - 10) {
                ball.xDirection *= -1;
//                ball.yDirection *= -1;
                System.out.println("Right side intersection");
            }

            ball.yDirection *= -1;
        }

        // MARK: Ball crosses bottom edge
        if (ball.y > getHeight()) {
            thread = null;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Loose or game over
            lives -= 1;
            if (lives != 0) {
                resetScene(false);

//                pause();
            } else {
                isRunning = !isRunning;
                System.out.println("Game Over!");
                removeKeyListener(this);
                Game game = Game.getInstance();
                game.gameOver(score);
                game.menuFrame.setVisible(true);
                game.frame.setVisible(false);
                game.frame.remove(this);
            }

//            pause();
        }

        bricks.forEach(brick -> {
            if (!brick.destroyed && ball.intersects(brick)) {
                brick.destroyed = true;
                if (ball.x + ball.size < brick.x + 5) {
                    ball.xDirection *= -1;
                    System.out.println("Left side hit");
                } else if (ball.x > brick.x + brick.width - 5) {
                    ball.xDirection *= -1;
                    System.out.println("Right side hit");
                } else if (ball.y + ball.size < brick.y + 5) {
                    ball.yDirection *= -1;
                    System.out.println("Top side hit");
                } else if (ball.y > brick.y + brick.height - 5) {
                    ball.yDirection *= -1;
                    System.out.println("Bottom side hit");
                } else {
                    ball.xDirection *= -1;
                    ball.yDirection *= -1;
                }
                levelScore++;
                score++;
                System.out.println(score);
            }
        });

        if (levelScore == bricksCount) {
            System.out.println("Level passed!");
            level += 1;
//            score += levelScore;
            levelScore = 0;
            if (lives != 3) lives+=1;
            System.out.println("Level: " + level);

            if (isRunning) {
                isRunning = !isRunning;
                thread = null;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resetScene(true);
                run();
                isRunning = !isRunning;
            }
        }
        repaint();
    }

    public void pause() {
        isPaused = !isPaused;

        if (isPaused) {
            ball.speed = 0;
        } else {
            ball.speed = 1;
        }
    }

    @Override
    public void run() {
//        resetScene();
//        pause();
        thread = new Thread(() -> {
            while (isRunning) {
                updateView();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }



    // MARK: Autogenerated methods for KeyListener

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && thread == null) {
            System.out.println("Space");
//            pause();
            thread = new Thread(() -> {
                while (isRunning) {
                    updateView();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x + paddle.width < getWidth()) {
            if (!isPaused) {
                paddle.x += 15;
                updateView();
//                repaint();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0) {
            if (!isPaused) {
                paddle.x -= 15;
                updateView();
//                repaint();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            pause();
        }
    }




    @Override
    public void keyReleased(KeyEvent e) {

    }
}
