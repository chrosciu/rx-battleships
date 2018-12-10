package com.chrosciu.rxbattleships.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Ship position on board
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ShipPosition {
    /**
     * Field where first ship field is located.
     * For horizontally placed ships it is the most left field,
     * for vertically ones - the most top one
     */
    public final Field firstField;
    /**
     * Size of ship
     */
    public final int size;
    /**
     * True if ship is placed horizontally, false - if vertically
     */
    public final boolean horizontal;
}
