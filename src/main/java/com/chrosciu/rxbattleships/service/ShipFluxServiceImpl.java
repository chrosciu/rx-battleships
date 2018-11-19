package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.Ship;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ShipFluxServiceImpl implements ShipFluxService {
    private final ShipPlacementService shipPlacementService;

    @Getter
    private Flux<Ship> shipFlux;
    private FluxSink<Ship> shipFluxSink;

    @PostConstruct
    private void init() {
        shipFlux = Flux.create(sink -> {
            shipFluxSink = sink;
            new Thread(this::placeShips).start();
        });
    }

    private void placeShips() {
        Arrays.stream(Constants.SHIP_SIZES)
                .forEach(size -> shipFluxSink.next(shipPlacementService.placeShip(size)));
        shipFluxSink.complete();
    }
}
