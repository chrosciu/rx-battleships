package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.service.BattleService;
import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.ShotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

@Component
@RequiredArgsConstructor
public class BoardCanvas extends JComponent {
    private final BattleService battleService;
    private final MouseAdapter mouseAdapter;

    private static final float FONT_SIZE = 60;

    private ShotResult[][] shotResults = new ShotResult[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    private boolean finished;

    @PostConstruct
    private void init() {
        finished = false;
        battleService.getBattleReadyMono()
                .subscribe(null, null, () -> addMouseListener(mouseAdapter));
        battleService.getShotResultFlux()
                .subscribe(shotResult -> {
                    shotResults[shotResult.x][shotResult.y] = shotResult.shotResult;
                    repaint();
                }, null, () ->  {
                    finished = true;
                    removeMouseListener(mouseAdapter);
                    repaint();
                });
    }

    @Override
    public void paintComponent(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);

        if (finished) {
            g.setColor(Color.GREEN);
        }

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

            for (char c = 'A'; c < 'A' + Constants.BOARD_SIZE; ++c) {
                g2.drawString(Character.toString(c),
                        Constants.CELL_SIZE + (c - 'A') * Constants.CELL_SIZE,
                        Constants.CELL_SIZE);
            }
            for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
                g2.drawString(Integer.toString(i),
                        0,
                        2 * Constants.CELL_SIZE + i * Constants.CELL_SIZE);
            }

            for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
                for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                    char c = getFieldChar(shotResults[x][y]);
                    g2.drawString(Character.toString(c),
                            Constants.CELL_SIZE + Constants.CELL_SIZE * x,
                            2 * Constants.CELL_SIZE + Constants.CELL_SIZE * y);
                }
            }

        }
    }

    private char getFieldChar(ShotResult shotResult) {
        if (null == shotResult) {
            return ' ';
        }
        switch (shotResult) {
            case MISSED:
                return 'O';
            case HIT:
                return 'H';
            case SUNK:
                return 'X';
            default:
                return ' ';
        }
    }
}
