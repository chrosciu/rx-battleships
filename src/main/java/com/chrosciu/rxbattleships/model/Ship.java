package com.chrosciu.rxbattleships.model;

/**
 * Ship representation (with hits counter)
 */
public class Ship {
    /**
     * Ship position on board
     */
    public final ShipPosition position;
    /**
     * Array describing whether a field which forms a ship has been already hit.
     * For horizontally placed ships fields are represented in array from left to right,
     * for vertical ones - from top to bottom
     */
    public final boolean[] hits;

    /**
     * Construct ship with given position
     * @param position - ship position
     */
    public Ship(ShipPosition position) {
        this.position = position;
        this.hits = new boolean[position.size];
    }
}
