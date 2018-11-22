package com.chrosciu.rxbattleships.model;

import lombok.RequiredArgsConstructor;


/**
 * Field representation on board
 * NOTE: (0,0) coordinates represent the top left cell on board
 */
@RequiredArgsConstructor
public class Field {
    /**
     * Horizontal coordinate of shot
     */
    public final int x;
    /**
     * Vertical coordinate of shot
     */
    public final int y;
}
