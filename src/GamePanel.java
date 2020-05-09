import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

// MARK: Main game UI elements projector
public class GamePanel extends JPanel implements KeyListener, Runnable, Paintable  {
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Thread thread;
    private boolean isPaused = false;
    private boolean isRunning = true;
    // Game metrics variables
    private int bricksCount = 0;
    public int level = 1;
    public int lives = 3;
    public int levelScore = 0;
    public int score = 0;

    // Game panel constructor
    public GamePanel() {
        resetScene(true);
        addKeyListener(this);
        setFocusable(true);
    }

    // MARK: Draw UI method calling
    @Override
    public void paint(Graphics g) {
        // Makes black background
        g.fillRect(0,0,Const.WINDOW_WIDTH,Const.WINDOW_HEIGHT);

        // Initializing game objects
        ball.paint(g);
        paddle.paint(g);
        bricks.forEach(block -> block.paint(g));

        // Compounding additional elements for metrics
        String levelOutput = String.format("Level %d: %d", level, levelScore);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri",Font.BOLD,16));
        g.drawString(levelOutput,20,20);
        g.drawString("Score: "+ score,Const.WINDOW_WIDTH/2 - 30,20);
        g.drawString("Lives: "+lives,Const.WINDOW_WIDTH - 80,20);
    }

    // MARK: reset scene method called when new game, lost life or level reset needed
    private void resetScene(boolean isNewLevel) {
        ball = new Ball();
        paddle = new Paddle();

        if (isNewLevel) {
            levelScore = 0;
            bricksCount = 0;
            //*/
            for(int i = 1; i < 8; ++i) {
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

    // MARK: Updates view
    public void updateView() {
        // Constantly updates ball position in certain direction and speed
        ball.x += ball.xDirection * ball.speed;
        ball.y += ball.yDirection * ball.speed;

        // Fixes issue when ball gets one axis move
        if (ball.xDirection == 0 || ball.yDirection == 0) {
            System.out.println("Vertical or perpendicular");
            ball.xDirection = -1;
            ball.yDirection = -1;
        }

        // Ball hits left or right edge
        if(ball.x < 0 || ball.x + ball.width > getWidth()) {
            ball.xDirection *= -1;
        }

        // Ball hits a top edge
        if(ball.y < 0) {
            ball.yDirection *= -1;
        }

        // Ball hits a paddle on top of the platform or on one of the sides
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

        // Ball crosses bottom edge
        if (ball.y > getHeight()) {
            lives -= 1;
            if (lives > 0) {
                resetScene(false);
            } else {
                // Sleep thread on game over
                try { Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isRunning = false;
                System.out.println("Game Over!");
                removeKeyListener(this);
                // Call game over method from singleton
                Game.getInstance().endGame(score);
            }
        }

        // MARK: Ball hits one or multiple blocks, handling collisions
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

        // Detect level passed
        if (levelScore == bricksCount) {
            System.out.println("Level passed!");
            level += 1;
            levelScore = 0;

            if (lives != 5) { lives += 1; }
            System.out.println("Level: " + level);

            try {
                // Sleep thread before new level
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resetScene(true);
        }
        // Repaint UI
        repaint();
    }

    // MARK: Pause game method
    public void pause() {
        isPaused = !isPaused;

        if (isPaused) {
            ball.speed = 0;
            System.out.println("Paused");
        } else {
            ball.speed = 1;
        }
    }

    // MARK: Implementing Runnable method
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


    // Key pressed listener
    @Override
    public void keyPressed(KeyEvent e) {
        // Start game using Space
        if (e.getKeyCode() == KeyEvent.VK_SPACE && thread == null) {
            run();
            System.out.println("Space Run");
        }
        // Paddle move right
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x + paddle.width < getWidth()) {
            if (!isPaused) {
                paddle.x += 25;
                updateView();
            }
        }
        // Paddle move left
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0) {
            if (!isPaused) {
                paddle.x -= 25;
                updateView();
            }
        }
        // Pause or resume using P key
        if (e.getKeyCode() == KeyEvent.VK_P && thread != null) {
            pause();
        }
    }

    // MARK: Autogenerated methods for KeyListener
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
