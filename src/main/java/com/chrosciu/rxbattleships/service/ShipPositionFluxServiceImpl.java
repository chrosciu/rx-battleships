package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.ShipPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ShipPositionFluxServiceImpl implements ShipPositionFluxService {
    private final ShipPlacementService shipPlacementService;
    private final ConstantsService constantsService;

    private Flux<ShipPosition> shipPositionFlux;

    public Flux<ShipPosition> getShipPositionFlux() {
        if (null == shipPositionFlux) {
            shipPositionFlux = createShipPositionFlux();
        }
        return shipPositionFlux;
    }

    private Flux<ShipPosition> createShipPositionFlux() {
        return Flux.create(this::placeShips).subscribeOn(Schedulers.single());
    }

    private void placeShips(FluxSink<ShipPosition> shipPositionFluxSink) {
        Arrays.stream(constantsService.getShipSizes())
                .forEach(size -> shipPositionFluxSink.next(shipPlacementService.placeShip(size)));
        shipPositionFluxSink.complete();
    }
}
