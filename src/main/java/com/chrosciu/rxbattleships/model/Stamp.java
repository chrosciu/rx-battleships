package com.chrosciu.rxbattleships.model;

import lombok.RequiredArgsConstructor;

/**
 * Stamp to be placed by GUI on board as a result of battle logic
 */
@RequiredArgsConstructor
public class Stamp {
    /**
     * Field where stamp should be placed
     */
    public final Field field;
    /**
     * Shot result that decides what stamp character has to be used
     */
    public final ShotResult result;
}
