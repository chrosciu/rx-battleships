package com.chrosciu.rxbattleships.model;

import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
class ShipImpl implements Ship {
    /**
     * Ship position on board
     */
    private final ShipPosition position;
    /**
     * Array describing whether a field which forms a ship has been already hit.
     * For horizontally placed ships fields are represented in array from left to right,
     * for vertical ones - from top to bottom
     */
    private final boolean[] hits;

    ShipImpl(ShipPosition position) {
        this.position = position;
        this.hits = new boolean[position.size];
    }

    @Override
    public ShotResult takeShot(Field field) {
        int index = getFieldIndex(field);
        if (index >= 0) {
            hits[index] = true;
            return isSunk() ? ShotResult.SUNK : ShotResult.HIT;
        }
        return ShotResult.MISSED;
    }

    @Override
    public boolean isSunk() {
        return IntStream.range(0, hits.length).allMatch(i -> hits[i]);
    }

    @Override
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
