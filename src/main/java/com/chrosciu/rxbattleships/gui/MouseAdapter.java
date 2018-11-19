package com.chrosciu.rxbattleships.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Convenient MouseListener interface with default empty implementation of all methods
 * Allows implementors to implement selected methods only
 */
public interface MouseAdapter extends MouseListener {
    @Override
    default void mousePressed(MouseEvent e) {
    }

    @Override
    default void mouseClicked(MouseEvent e) {
    }

    @Override
    default void mouseReleased(MouseEvent e) {
    }

    @Override
    default void mouseEntered(MouseEvent e) {
    }

    @Override
    default void mouseExited(MouseEvent e) {
    }
}
