package com.chrosciu.rxbattleships;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
@RequiredArgsConstructor
public class BoardMouseAdapter extends MouseAdapter {
    private final BattleService battleService;

    @Setter
    private BoardCanvas boardCanvas;

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / Constants.CELL_SIZE - 1;
        int y = e.getY() / Constants.CELL_SIZE - 1;
        if (x >= 0 && x < Constants.BOARD_SIZE && y >= 0 && y < Constants.BOARD_SIZE) {
            battleService.takeShot(x, y);
            boardCanvas.repaint();
        }
    }
}
