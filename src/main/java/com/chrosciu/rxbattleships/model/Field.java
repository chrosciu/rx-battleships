package com.chrosciu.rxbattleships.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * Field representation on board
 * NOTE: (0,0) coordinates represent the top left cell on board
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
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
