package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.Shot;

/**
 * Listener used to be notified about performed shots
 */
public interface ShotListener {
    /**
     * Shot event
     * @param shot - shot data
     */
    void shotPerformed(Shot shot);
}
