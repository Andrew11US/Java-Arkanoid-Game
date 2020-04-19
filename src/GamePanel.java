import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, Runnable, Paintable  {
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Image image;
    private Thread thread;
    // Pauses the game, changing the ball.speed to 0, (recoverable action)
    private boolean isPaused = true;
    // Stops the game breaking while loop, not recoverable!
    private boolean isRunning = true;
    private int bricksCount = 0;
    public int level = 1;
    public int lives = 3;
    public int levelScore = 0;
    public int score = 0;

    public GamePanel() {
        resetScene(true);
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        // MARK: Makes black background
        g.fillRect(0,0,Const.WINDOW_WIDTH,Const.WINDOW_HEIGHT);
        ball.paint(g);
        paddle.paint(g);
        bricks.forEach(block -> block.paint(g));
        String levelOutput = String.format("Level %d: %d", level, levelScore);

        g.drawImage(image, 0, 0, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri",Font.BOLD,16));
        g.drawString(levelOutput,20,20);
        g.drawString("Score: "+ score,Const.WINDOW_WIDTH/2 - 30,20);
        g.drawString("Lives: "+lives,Const.WINDOW_WIDTH - 80,20);
    }

    private void resetScene(boolean isNewLevel) {
        ball = new Ball();
        paddle = new Paddle();

        if (isNewLevel) {
            levelScore = 0;
            bricksCount = 0;
            //*/
            for(int i = 1; i < 11; ++i) {
                for(int j = 1; j < 2 + ((level-1) % 10); ++j) {
                    bricks.add(new Brick(i*(Const.BRICK_WIDTH+10),j*(Const.BRICK_HEIGHT+10)));
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
            ball.xDirection = -1;
            ball.yDirection = -1;
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
                System.out.println("Left side intersection");
            } else if (ball.x > paddle.x + paddle.width - 10) {
                ball.xDirection *= -1;
                System.out.println("Right side intersection");
            }

            ball.yDirection *= -1;
        }

        // MARK: Ball crosses bottom edge
        if (ball.y > getHeight()) {
            lives -= 1;
            if (lives != 0) {
                resetScene(false);
            } else {
                try { Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isRunning = false;
                System.out.println("Game Over!");
                removeKeyListener(this);
                // Get Game instance to call gameOver method
                Game game = Game.getInstance();
                game.gameOver(score);
//                game.menuFrame.setVisible(true);
//                game.frame.setVisible(false);
//                game.frame.remove(this);
            }
        }

        // MARK: Ball hits one or multiple blocks, handling collision
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
            levelScore = 0;

            if (lives != 5) {
                lives+=1;
            }
            System.out.println("Level: " + level);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resetScene(true);
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
        thread = new Thread(() -> {
            while (isRunning) {
                updateView();
                try {
                    Thread.sleep(Const.GAME_SPEED);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // MARK: Autogenerated methods for KeyListener
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && thread == null) {
            run();
            pause();
            System.out.println("Space Run");
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x + paddle.width < getWidth()) {
            if (!isPaused) {
                paddle.x += 25;
                updateView();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0) {
            if (!isPaused) {
                paddle.x -= 25;
                updateView();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P && thread != null) {
            pause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
