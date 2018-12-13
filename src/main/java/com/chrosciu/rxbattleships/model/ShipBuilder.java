package com.chrosciu.rxbattleships.model;

public class ShipBuilder {
    /**
     * Construct ship with given position
     * @param position - position of ship
     * @return - Constructed ship
     */
    static public Ship buildShip(ShipPosition position) {
        return new ShipImpl(position);
    }
}
