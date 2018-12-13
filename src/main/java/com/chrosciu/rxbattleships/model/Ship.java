package com.chrosciu.rxbattleships.model;

import java.util.List;

/**
 * Ship representation (with hits counter)
 */
public interface Ship {
    /**
     * Take shot at given field and return shot result regarding this ship
     * @param field - field where shot is taken
     * @return - Result of shot for given ship
     */
    ShotResult takeShot(Field field);

    /**
     * Check if this ship is already sunk
     * @return - true if ship is sunk, false otherwise
     */
    boolean isSunk();

    /**
     * Get all fields which form this ship
     * @return - fields described above
     */
    List<Field> getAllFields();
}
