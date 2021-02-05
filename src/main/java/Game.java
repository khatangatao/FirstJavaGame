import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private boolean running;
    public static int WIDTH = 400;
    public static int HEIGHT = 300;
    public static String NAME = "TUTORIAL 1";
    public static Sprite hero;
    protected boolean leftPressed = false;
    protected boolean rightPressed = false;
    protected boolean upPressed = false;
    protected boolean downPressed = false;
    private static int x = 0;
    private static int y = 50;

    public class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
        }
    }


    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return sprite;
    }

    public void run() {
    // main game cycle
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while(running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            update(delta);
            render();
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(Game.NAME);
//        выход из приложения по нажатию клавиши ESC
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
//        добавляем холст на наш фрейм
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void init() {
        hero = getSprite("man.png");
        addKeyListener(new KeyInputHandler());
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
//            создаем BufferStrategy для нашего холста
            createBufferStrategy(2);
            requestFocus();
            return;
        }

//        получаем Graphics из созданной нами BufferStrategy
        Graphics g = bs.getDrawGraphics();
//        выбрать цвет
        g.setColor(Color.BLACK);
//        заполнить прямоугольник
        g.fillRect(0,0, getWidth(), getHeight());
        hero.draw(g, x, y);
        g.dispose();
//        показать
        bs.show();
    }

    public void update(long delta) {
        if (leftPressed == true) {
            x--;
            if (x < 0) {
                x = 0;
            }
        }
        if (rightPressed == true) {
            x++;

            if (x >= WIDTH - hero.getWidth()) {
                x = WIDTH - hero.getWidth();
            }
        }

        if (downPressed == true) {
            y++;

            if (y >= HEIGHT) {
                y = HEIGHT - hero.getHeight();
            }


        }

        if (upPressed == true) {
            y--;

            if (y <= 0) {
                y = 0;
            }

        }



    }
}
