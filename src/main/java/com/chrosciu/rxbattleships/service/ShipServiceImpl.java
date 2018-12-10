package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.Ship;
import com.chrosciu.rxbattleships.model.ShotResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ShipServiceImpl implements ShipService {
    @Override
    public ShotResult takeShot(Ship ship, Field field) {
        int index = getFieldIndex(ship, field);
        if (index >= 0) {
            ship.hits[index] = true;
            return isSunk(ship) ? ShotResult.SUNK : ShotResult.HIT;
        }
        return ShotResult.MISSED;
    }

    @Override
    public boolean isSunk(Ship ship) {
        return IntStream.range(0, ship.hits.length).allMatch(i -> ship.hits[i]);
    }

    @Override
    public List<Field> getAllFields(Ship ship) {
        return IntStream.range(0, ship.position.size).mapToObj(value -> ship.position.horizontal ?
                new Field(ship.position.firstField.x + value, ship.position.firstField.y)
                : new Field(ship.position.firstField.x, ship.position.firstField.y + value)).collect(Collectors.toList());
    }

    private int getFieldIndex(Ship ship, Field field) {
        int index = -1;
        if (ship.position.horizontal) {
            if (ship.position.firstField.y == field.y) {
                index = field.x - ship.position.firstField.x;
            }
        } else {
            if (ship.position.firstField.x == field.x) {
                index = field.y - ship.position.firstField.y;
            }
        }
        if (index >= ship.position.size) {
            index = -1;
        }
        return index;
    }
}
