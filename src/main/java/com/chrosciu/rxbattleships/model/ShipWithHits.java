package com.chrosciu.rxbattleships.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@ToString
/**
 * Class representing ship with hits applied to it
 */
public class ShipWithHits {
    @NonNull private final Ship ship;
    private int hits = 0;

    /**
     * Take given shot and return shot with its results regarding this ship
     * @param shot - shot to take
     * @return - List of shots with results.
     * If shot was missed (not hit the ship) or hit (but not sunk the ship) only one shot is returned
     * If shot sunk the ship shots regarding all ship fields are returnd with SUNK status
     */
    public List<ShotWithResult> takeShot(Shot shot) {
        if (!isHit(shot)) {
            return Collections.singletonList(ShotWithResult.of(shot, ShotResult.MISSED));
        }
        if (!isSunk()) {
            ++hits;
        }
        if(isSunk()) {
            List<Shot> shots = IntStream.range(0, ship.size).mapToObj(value -> ship.horizontal ?
                    Shot.builder().x(ship.x + value).y(ship.y).build()
                    : Shot.builder().x(ship.x).y(ship.y + value).build()).collect(Collectors.toList());
            return shots.stream().map(shot1 -> ShotWithResult.of(shot1, ShotResult.SUNK)).collect(Collectors.toList());
        } else {
            return Collections.singletonList(ShotWithResult.of(shot, ShotResult.HIT));
        }
    }

    /**
     * Check if ship is already sunk
     * @return - true if ship  is sunk, false otherwise
     */
    public boolean isSunk() {
        return hits >= ship.size;
    }

    /**
     * Check if given shot affects the ship
     * @param shot - shot to check
     * @return - true if shot affects the ship, false otherwise
     */
    public boolean isHit(Shot shot) {
        if (ship.horizontal) {
            return ship.y == shot.y && shot.x >= ship.x && shot.x < ship.x + ship.size;
        } else {
            return ship.x == shot.x && shot.y >= ship.y && shot.y < ship.y + ship.size;
        }
    }







}
