package com.chrosciu.rxbattleships.model;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
/**
 * Ship representation on board
 */
public class Ship {
    /**
     * Horizontal coordinate of first ship cell
     * For horizontal ships first cell is the most left one, for vertical ones - the most top one
     */
    public final int x;
    /**
     * Vertical coordinate of first ship cell
     */
    public final int y;
    /**
     * Size of ship
     */
    public final int size;
    /**
     * True if ship is placed horizontally, false - if vertically
     */
    public final boolean horizontal;
}
