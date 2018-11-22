package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.ShipPosition;

/**
 * Service handling ship placement on board logic
 */
public interface ShipPlacementService {
    /**
     * Place ship with given size on board
     * NOTE: This is expensive operation and should be called in separate thread
     * @param size - size of ship to be placed
     * @return - position of placed ship
     */
    ShipPosition placeShip(int size);
}
