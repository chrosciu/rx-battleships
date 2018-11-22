package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.ShotResult;
import com.chrosciu.rxbattleships.model.Stamp;
import com.chrosciu.rxbattleships.service.BattleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.chrosciu.rxbattleships.config.Constants.BOARD_SIZE;
import static com.chrosciu.rxbattleships.config.Constants.CELL_SIZE;
import static com.chrosciu.rxbattleships.config.Constants.FONT_SIZE;
import static com.chrosciu.rxbattleships.model.ShotResult.HIT;
import static com.chrosciu.rxbattleships.model.ShotResult.MISSED;
import static com.chrosciu.rxbattleships.model.ShotResult.SUNK;
import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

@Component
@RequiredArgsConstructor
public class BoardCanvas extends JComponent {
    private final BattleService battleService;
    private final MouseAdapter mouseAdapter;

    private static final String WAITING_FOR_SHIPS = "Waiting for ships...";
    private static final String SHIPS_READY = "Ships ready !";

    private static final Map<ShotResult, Character> SHOT_RESULT_CODES =
            new HashMap<ShotResult, Character>(){{
        put(MISSED, 'O');
        put(HIT, 'H');
        put(SUNK, 'X');
    }};
    private static final Character NO_SHOT_RESULT_CODE = ' ';

    private class TimeMeasurer {
        long startTime;
        long stopTime;

        private void start() {
            startTime = System.currentTimeMillis();
        }

        private void stop() {
            stopTime = System.currentTimeMillis();
            alert( "Time elapsed: " + (stopTime - startTime) / 1000 + " s.");
        }
    }

    @AllArgsConstructor
    @Getter
    private enum GameState {
        INITIAL(BLUE),
        STARTED(BLACK),
        FINISHED(GREEN),
        FAILED(RED);

        private Color color;
    }

    private ShotResult[][] shotResults = new ShotResult[BOARD_SIZE][BOARD_SIZE];
    private TimeMeasurer timeMeasurer = new TimeMeasurer();
    private GameState gameState = GameState.INITIAL;

    @PostConstruct
    private void init() {
        battleService.getShipsReadyMono()
                .subscribe(null, this::onError, this::onShipsReady);
    }

    private void onShipsReady() {
        alert(SHIPS_READY);
        battleService.getShotResultFlux()
                .subscribe(this::onShotPerformed, this::onError, this::onAllShipsSunk);
        timeMeasurer.start();
        gameState = GameState.STARTED;
        addMouseListener(mouseAdapter);
        repaint();
    }

    private void onShotPerformed(Stamp stamp) {
        shotResults[stamp.field.x][stamp.field.y] = stamp.result;
        repaint();
    }

    private void onAllShipsSunk() {
        removeMouseListener(mouseAdapter);
        gameState = GameState.FINISHED;
        timeMeasurer.stop();
        repaint();
    }

    private void onError(Throwable t) {
        gameState = GameState.FAILED;
        alert(t.getMessage());
    }

    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        setFont(graphics2D);
        setColor(graphics2D);
        drawLabels(graphics2D);
        if (GameState.INITIAL.equals(gameState)) {
            drawMessage(graphics2D, WAITING_FOR_SHIPS);
        } else {
            drawFields(graphics2D);
        }
    }

    private void setFont(Graphics2D graphics2D) {
        Font currentFont = graphics2D.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        graphics2D.setFont(newFont);
        graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    }

    private void setColor(Graphics2D graphics2D) {
        graphics2D.setColor(gameState.getColor());
    }

    private void drawLabels(Graphics2D graphics2D) {
        IntStream.range(0, BOARD_SIZE).forEach(i -> {
            graphics2D.drawString(Character.toString((char)('A' + i)), (i + 1) * CELL_SIZE, CELL_SIZE);
            graphics2D.drawString(Integer.toString(i), 0,  (i + 2) * CELL_SIZE);
        });
    }

    private void drawMessage(Graphics2D graphics2D, String message) {
        graphics2D.drawString(message, CELL_SIZE, 2 * CELL_SIZE);
    }

    private void drawFields(Graphics2D graphics2D) {
        IntStream.range(0, BOARD_SIZE).forEach(i -> {
            IntStream.range(0, BOARD_SIZE).forEach(j -> drawField(graphics2D, i, j));
        });
    }

    private void drawField(Graphics2D graphics2D, int i, int j) {
        graphics2D.drawString(Character.toString(getShotResultCode(shotResults[i][j])),
                (i + 1) * CELL_SIZE, (j + 2) * CELL_SIZE);
    }

    private char getShotResultCode(ShotResult shotResult) {
        return Optional.ofNullable(shotResult)
                .map(SHOT_RESULT_CODES::get).orElse(NO_SHOT_RESULT_CODE);
    }
}
