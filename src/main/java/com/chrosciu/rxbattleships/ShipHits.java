package com.chrosciu.rxbattleships;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ShipHits {
    @NonNull private final Ship ship;
    private int hits = 0;

    public CellStatus takeShot(Shot shot) {
        if (isHit(shot)) {
            ++hits;
            if (isSunk()) {
                return CellStatus.SUNK;
            } else {
                return CellStatus.HIT;
            }
        } else {
            return CellStatus.MISSED;
        }
    }

    public boolean isSunk() {
        return hits >= ship.size;
    }

    private boolean isHit(Shot shot) {
        if (ship.horizontal) {
            return ship.y == shot.y && shot.x >= ship.x && shot.x < ship.x + ship.size;
        } else {
            return ship.x == shot.x && shot.y >= ship.y && shot.y < ship.y + ship.size;
        }
    }






}
