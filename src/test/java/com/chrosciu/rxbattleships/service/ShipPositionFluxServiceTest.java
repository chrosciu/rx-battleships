package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.ShipPosition;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipPositionFluxServiceTest {
    private ConstantsService constantsService = mock(ConstantsService.class);
    private ShipPlacementService shipPlacementService = mock(ShipPlacementService.class);

    private ShipPositionFluxService shipPositionFluxService = new ShipPositionFluxServiceImpl(shipPlacementService, constantsService);

    private final int[] shipSizes = new int[] {2, 3};
    private final ShipPosition firstShipPosition = new ShipPosition(new Field(0, 0), shipSizes[0], true);
    private final ShipPosition secondShipPosition = new ShipPosition(new Field(2, 3), shipSizes[1], false);

    @Test
    public void getShipPositionFluxTest() {
        //given
        when(constantsService.getShipSizes()).thenReturn(shipSizes);
        when(shipPlacementService.placeShip(shipSizes[0])).thenReturn(firstShipPosition);
        when(shipPlacementService.placeShip(shipSizes[1])).thenReturn(secondShipPosition);

        //when
        Flux<ShipPosition> shipPositionFlux = shipPositionFluxService.getShipPositionFlux();

        //then
        StepVerifier.create(shipPositionFlux)
                .expectNext(firstShipPosition, secondShipPosition)
                .verifyComplete();
    }
}
