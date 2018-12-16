package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.ShipPosition;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipPlacementServiceTest {
    private RandomService randomService = mock(RandomService.class);

    private ShipPlacementService shipPlacementService = new ShipPlacementServiceImpl(randomService);

    @Test
    public void placeShipTest() {
        //given
        when(randomService.booleanRandom()).thenReturn(true, true, true, true, false);
        when(randomService.intRandom(Constants.BOARD_SIZE - (3 - 1))).thenReturn(1, 2, 0, 4);
        when(randomService.intRandom(Constants.BOARD_SIZE - (2 - 1))).thenReturn(7);
        when(randomService.intRandom(Constants.BOARD_SIZE)).thenReturn(3, 3, 2, 6, 9);

        //when
        ShipPosition firstShipPosition = shipPlacementService.placeShip(3);

        //then
        assertEquals(new ShipPosition(new Field(1, 3), 3, true), firstShipPosition);

        //when
        ShipPosition secondShipPosition = shipPlacementService.placeShip(3);

        //then
        assertEquals(new ShipPosition(new Field(4, 6), 3, true), secondShipPosition);

        //when
        ShipPosition thirdShipPosition = shipPlacementService.placeShip(2);

        //then
        assertEquals(new ShipPosition(new Field(9, 7), 2, false), thirdShipPosition);
    }
}
