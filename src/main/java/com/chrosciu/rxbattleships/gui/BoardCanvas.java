package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.ShotResult;
import com.chrosciu.rxbattleships.model.ShotWithResult;
import com.chrosciu.rxbattleships.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.stream.IntStream;

import static com.chrosciu.rxbattleships.config.Constants.BOARD_SIZE;
import static com.chrosciu.rxbattleships.config.Constants.CELL_SIZE;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

@Component
@RequiredArgsConstructor
public class BoardCanvas extends JComponent {
    private final BattleService battleService;
    private final MouseAdapter mouseAdapter;

    private static final float FONT_SIZE = 60;

    private ShotResult[][] shotResults = new ShotResult[BOARD_SIZE][BOARD_SIZE];
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
        setFont(graphics2D);
        setColor(graphics2D);
        drawLabels(graphics2D);
        drawFields(graphics2D);
    }

    private void setFont(Graphics2D graphics2D) {
        Font currentFont = graphics2D.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        graphics2D.setFont(newFont);
        graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    }

    private void setColor(Graphics2D graphics2D) {
        Color color = started ? (finished ? Color.GREEN : Color.BLACK) : Color.RED;
        graphics2D.setColor(color);
    }

    private void drawLabels(Graphics2D graphics2D) {
        IntStream.range(0, BOARD_SIZE).forEach(i -> {
            graphics2D.drawString(Character.toString((char)('A' + i)), (i + 1) * CELL_SIZE, CELL_SIZE);
            graphics2D.drawString(Integer.toString(i), 0,  (i + 2) * CELL_SIZE);
        });
    }

    private void drawFields(Graphics2D graphics2D) {
        IntStream.range(0, BOARD_SIZE).forEach(i -> {
            IntStream.range(0, BOARD_SIZE).forEach(j -> drawField(graphics2D, i, j));
        });
    }

    private void drawField(Graphics2D graphics2D, int i, int j) {
        graphics2D.drawString(Character.toString(getFieldChar(shotResults[i][j])),
                (i + 1) * CELL_SIZE, (j + 2) * CELL_SIZE);
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
