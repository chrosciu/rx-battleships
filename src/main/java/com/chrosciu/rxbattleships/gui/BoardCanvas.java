package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.ShotWithResult;
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
    private boolean started;
    private boolean finished;

    @PostConstruct
    private void init() {
        finished = false;
        battleService.getBattleReadyMono()
                .subscribe(null, null, this::onShipsReady);
        battleService.getShotResultFlux()
                .subscribe(this::onShotPerformed, null, this::onAllShipsSunk);
    }

    private void onShipsReady() {
        started = true;
        addMouseListener(mouseAdapter);
        repaint();
    }

    private void onShotPerformed(ShotWithResult shotWithResult) {
        shotResults[shotWithResult.x][shotWithResult.y] = shotWithResult.shotResult;
        repaint();
    }

    private void onAllShipsSunk() {
        finished = true;
        removeMouseListener(mouseAdapter);
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        Font currentFont = graphics2D.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        graphics2D.setFont(newFont);
        graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        Color color = started ? (finished ? Color.GREEN : Color.BLACK) : Color.RED;
        graphics.setColor(color);
        drawLabels(graphics2D);
        drawFields(graphics2D);
    }

    private void drawLabels(Graphics2D graphics2D) {
        for (char c = 'A'; c < 'A' + Constants.BOARD_SIZE; ++c) {
            graphics2D.drawString(Character.toString(c),
                    Constants.CELL_SIZE + (c - 'A') * Constants.CELL_SIZE,
                    Constants.CELL_SIZE);
        }
        for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
            graphics2D.drawString(Integer.toString(i),
                    0,
                    2 * Constants.CELL_SIZE + i * Constants.CELL_SIZE);
        }
    }

    private void drawFields(Graphics2D graphics2D) {
        for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
            for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                char c = getFieldChar(shotResults[x][y]);
                graphics2D.drawString(Character.toString(c),
                        Constants.CELL_SIZE + Constants.CELL_SIZE * x,
                        2 * Constants.CELL_SIZE + Constants.CELL_SIZE * y);
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
