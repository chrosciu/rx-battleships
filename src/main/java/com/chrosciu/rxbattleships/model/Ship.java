package com.chrosciu.rxbattleships.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private boolean[] hits;

    /**
     * Construct ship with given position
     * @param position - ship position
     */
    public Ship(ShipPosition position) {
        this.position = position;
        this.hits = new boolean[position.size];
    }

    /**
     * Take shot at given field and return shot result regarding this ship
     * @param field - field where shot is taken
     * @return - Result of shot for given ship
     */
    public ShotResult takeShot(Field field) {
        int index = getFieldIndex(field);
        if (index >= 0) {
            hits[index] = true;
            return isSunk() ? ShotResult.SUNK : ShotResult.HIT;
        }
        return ShotResult.MISSED;
    }

    /**
     * Check if ship is already sunk
     * @return - true if ship  is sunk, false otherwise
     */
    public boolean isSunk() {
        return IntStream.range(0, hits.length).allMatch(i -> hits[i]);
    }

    /**
     * Get all fields which form this ship
     * @return - fields described above
     */
    public List<Field> getAllFields() {
        return IntStream.range(0, position.size).mapToObj(value -> position.horizontal ?
                new Field(position.firstField.x + value, position.firstField.y)
                : new Field(position.firstField.x, position.firstField.y + value)).collect(Collectors.toList());
    }

    private int getFieldIndex(Field field) {
        int index = -1;
        if (position.horizontal) {
            if (position.firstField.y == field.y) {
                index = field.x - position.firstField.x;
            }
        } else {
            if (position.firstField.x == field.x) {
                index = field.y - position.firstField.y;
            }
        }
        if (index >= position.size) {
            index = -1;
        }
        return index;
    }







}
