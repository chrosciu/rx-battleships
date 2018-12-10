package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.Ship;
import com.chrosciu.rxbattleships.model.ShotResult;

import java.util.List;

public interface ShipService {
    /**
     * Take shot at given field and return shot result regarding given ship
     * @param ship - ship
     * @param field - field where shot is taken
     * @return - Result of shot for given ship
     */
    ShotResult takeShot(Ship ship, Field field);

    /**
     * Check if given ship is already sunk
     * @param ship - ship
     * @return - true if ship is sunk, false otherwise
     */
    boolean isSunk(Ship ship);

    /**
     * Get all fields which form given ship
     * @param ship - ship
     * @return - fields described above
     */
    List<Field> getAllFields(Ship ship);
}
