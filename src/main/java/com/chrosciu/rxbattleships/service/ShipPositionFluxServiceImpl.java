package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.ShipPosition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ShipPositionFluxServiceImpl implements ShipPositionFluxService {
    private final ShipPlacementService shipPlacementService;

    @Getter
    private Flux<ShipPosition> shipPositionFlux;
    private FluxSink<ShipPosition> shipPositionFluxSink;

    @PostConstruct
    private void init() {
        shipPositionFlux = Flux.create(sink -> {
            shipPositionFluxSink = sink;
            new Thread(this::placeShips).start();
        });
    }

    private void placeShips() {
        Arrays.stream(Constants.SHIP_SIZES)
                .forEach(size -> shipPositionFluxSink.next(shipPlacementService.placeShip(size)));
        shipPositionFluxSink.complete();
    }
}
