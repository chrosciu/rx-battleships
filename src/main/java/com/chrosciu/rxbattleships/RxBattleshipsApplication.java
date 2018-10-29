package com.chrosciu.rxbattleships;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

@SpringBootApplication
public class RxBattleshipsApplication extends JFrame {

    public RxBattleshipsApplication() {
        initUI();
    }

    private void initUI() {
        Container contentPane = getContentPane();
        MyCanvas myCanvas = new MyCanvas();
        contentPane.add(myCanvas);
        setSize(580, 620);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(RxBattleshipsApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            RxBattleshipsApplication ex = ctx.getBean(RxBattleshipsApplication.class);
            ex.setVisible(true);
        });
    }
}

class MyCanvas extends JComponent {

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX() / CELL_SIZE - 1;
            int y = e.getY() / CELL_SIZE - 1;
            if (x >=0 && x < SIZE && y >= 0 && y < SIZE) {
                if (!shots[x][y]) {
                    shots[x][y] = true;
                    checkFinished();
                    repaint();
                }
            }
        }
    }

    private int SIZE = 10;

    private final int CELL_SIZE = 50;

    private final boolean[][] ships = new boolean[SIZE][SIZE];
    private final boolean[][] shots = new boolean[SIZE][SIZE];

    private boolean finished;

    MyCanvas() {
        placeShips();
        this.addMouseListener(new MyMouseAdapter());
    }

    void placeShips() {
        placeShip(4);
        placeShip(3);
        placeShip(3);
        placeShip(2);
        placeShip(2);
        placeShip(2);
        placeShip(1);
        placeShip(1);
        placeShip(1);
        placeShip(1);
    }

    void placeShip(int size) {
        while (true) {
            int x;
            int y;
            boolean horizontal = Math.random() < 0.5;
            int a = (int) ((SIZE + 1 - size) * Math.random()) % (SIZE + 1 - size);
            int b = (int) (SIZE * Math.random()) % SIZE;
            //System.out.printf("%d %d %d%n", a, b, size);
            if (horizontal) {
                x = a;
                y = b;
            } else {
                x = b;
                y = a;
            }
            //System.out.printf("%b %d %d %d%n", horizontal, x, y, size);
            if (!isCollision(horizontal, x, y, size)) {
                for (int i = 0; i < size; ++i) {
                    if (horizontal) {
                        ships[x+i][y] = true;
                    } else {
                        ships[x][y+i] = true;
                    }
                }
                break;
            }
        }

    }

    boolean isCollision(boolean horizontal, int x, int y, int size) {
        for (int i = 0; i < size; ++i) {
            int a = horizontal ? x + i : x;
            int b = horizontal ? y : y + i;
            if (isCollision(a, b)) {
                return true;
            }
        }
        return false;
    }

    boolean isCollision(int x, int y) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int a = x + i;
                int b = y + j;
                if (a >= 0 && a < SIZE && b >= 0 && b < SIZE && ships[a][b]) {
                    //System.out.printf("isCollision(%d,%d) -> %b%n", x, y, true);
                    return true;
                }
            }
        }
        //System.out.printf("isCollision(%d,%d) -> %b%n", x, y, false);
        return false;
    }

    void checkFinished() {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                if (ships[x][y] && !shots[x][y]) {
                    return;
                }
            }
        }
        finished = true;
    }

    @Override
    public void paintComponent(Graphics g) {

        float FONT_SIZE = 60;

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);

        if (finished) {
            g.setColor(Color.RED);
        }

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

            for (char c = 'A'; c < 'A' + SIZE; ++c) {
                g2.drawString(Character.toString(c), CELL_SIZE + (c - 'A') * CELL_SIZE, CELL_SIZE);
            }
            for (int i = 0; i < SIZE; ++i) {
                g2.drawString(Integer.toString(i), 0, 2 * CELL_SIZE + i * CELL_SIZE);
            }

            for (int x = 0; x < SIZE; ++x) {
                for (int y = 0; y < SIZE; ++y) {
                    char c = shots[x][y] ? ships[x][y] ? 'X' : 'O' : ' ';
                    //char c = ships[x][y] ? 'X' : ' ';
                    g2.drawString(Character.toString(c), CELL_SIZE + CELL_SIZE * x, 2 * CELL_SIZE + CELL_SIZE * y);
                }
            }

        }
    }
}

