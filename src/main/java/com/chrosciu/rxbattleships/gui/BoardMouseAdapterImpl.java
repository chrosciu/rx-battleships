package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.Shot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;

import static com.chrosciu.rxbattleships.config.Constants.BOARD_SIZE;
import static com.chrosciu.rxbattleships.config.Constants.CELL_SIZE;

@Component
@RequiredArgsConstructor
public class BoardMouseAdapterImpl implements BoardMouseAdapter {
    private ShotListener shotListener;

    @Override
    public void registerShotListener(ShotListener shotListener) {
        this.shotListener = shotListener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (shotListener != null) {
            int x = (e.getX() / CELL_SIZE) - 1;
            int y = (e.getY() / CELL_SIZE) - 1;
            if ((x >= 0) && (x < BOARD_SIZE) && (y >= 0) && (y < BOARD_SIZE)) {
                Shot shot = Shot.builder().x(x).y(y).build();
                shotListener.shotPerformed(shot);
            }
        }
    }


}
