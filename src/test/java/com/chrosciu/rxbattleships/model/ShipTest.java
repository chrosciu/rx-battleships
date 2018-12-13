package com.chrosciu.rxbattleships.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {
    private final Field badField = new Field(0, 0);
    private final Field firstField = new Field(1, 1);
    private final Field secondField = new Field(2, 1);
    private final ShipPosition position = new ShipPosition(firstField, 2, true);
    private Ship ship;

    @Before
    public void setup() {
        ship = ShipBuilder.buildShip(position);
    }

    @Test
    public void missedResultTest() {
        //when
        ShotResult result = ship.takeShot(badField);

        //then
        assertEquals(ShotResult.MISSED, result);
        assertFalse(ship.isSunk());
    }

    @Test
    public void hitResultTest() {
        //when
        ShotResult result = ship.takeShot(firstField);

        //then
        assertEquals(ShotResult.HIT, result);
        assertFalse(ship.isSunk());
    }

    @Test
    public void sunkResultTest() {
        //when
        ship.takeShot(firstField);
        ShotResult result = ship.takeShot(secondField);

        //then
        assertEquals(ShotResult.SUNK, result);
        assertTrue(ship.isSunk());
    }

    @Test
    public void getAllFieldsTest() {
        //when
        List<Field> fields = ship.getAllFields();

        //then
        assertEquals(Arrays.asList(firstField, secondField), fields);
    }
}
