package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.ShipPosition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipPositionFluxServiceImpl implements ShipPositionFluxService {
    private final ShipPlacementService shipPlacementService;
    private final ConstantsService constantsService;

    @Getter
    private Flux<ShipPosition> shipPositionFlux;
    private FluxSink<ShipPosition> shipPositionFluxSink;

    @PostConstruct
    private void init() {
        shipPositionFlux = Flux.<ShipPosition>create(sink -> {
            shipPositionFluxSink = sink;
            placeShips();
        }).subscribeOn(Schedulers.single());
    }

    private void placeShips() {
        Arrays.stream(Constants.SHIP_SIZES)
                .forEach(size -> shipPositionFluxSink.next(shipPlacementService.placeShip(size)));
        shipPositionFluxSink.complete();
    }
}
