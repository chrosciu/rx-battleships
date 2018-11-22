package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;

import static com.chrosciu.rxbattleships.config.Constants.BOARD_SIZE;
import static com.chrosciu.rxbattleships.config.Constants.CELL_SIZE;

@Component
@RequiredArgsConstructor
public class BoardMouseAdapterImpl implements BoardMouseAdapter {
    private FieldClickListener fieldClickListener;

    @Override
    public void registerFieldListener(FieldClickListener fieldClickListener) {
        this.fieldClickListener = fieldClickListener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (fieldClickListener != null) {
            int x = (e.getX() / CELL_SIZE) - 1;
            int y = (e.getY() / CELL_SIZE) - 1;
            if ((x >= 0) && (x < BOARD_SIZE) && (y >= 0) && (y < BOARD_SIZE)) {
                fieldClickListener.onFieldClicked(new Field(x, y));
            }
        }
    }


}
