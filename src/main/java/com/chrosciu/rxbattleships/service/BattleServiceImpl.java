package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.Ship;
import com.chrosciu.rxbattleships.model.ShipPosition;
import com.chrosciu.rxbattleships.model.ShotResult;
import com.chrosciu.rxbattleships.model.Stamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ShipPositionFluxService shipPositionFluxService;
    private final FieldFluxService fieldFluxService;

    private List<Ship> ships = new ArrayList<>();

    @Getter
    private Mono<Void> shipsReadyMono;

    @Getter
    private Flux<Stamp> stampFlux;

    @RequiredArgsConstructor
    private class AffectedShipAndResult {
        public final Ship ship;
        public final ShotResult result;
    }

    @PostConstruct
    private void init() {
        shipsReadyMono = shipPositionFluxService.getShipPositionFlux()
                .doOnNext(this::insertShipWithPosition).then();
        stampFlux = fieldFluxService.getFieldFlux()
                .map(this::getShotResultsAfterShot)
                .takeUntil(this::allSunk)
                .flatMap(Flux::fromIterable);
    }

    private List<Stamp> getShotResultsAfterShot(Field field) {
        AffectedShipAndResult affectedShipAndResult = getAffectedShipAndResultAfterShot(field);
        ShotResult result = affectedShipAndResult.result;
        if (ShotResult.SUNK == result) {
            return shipService.getAllFields(affectedShipAndResult.ship).stream()
                    .map(f -> new Stamp(f, ShotResult.SUNK)).collect(Collectors.toList());
        } else {
            return Collections.singletonList(new Stamp(field, result));
        }
    }

    private AffectedShipAndResult getAffectedShipAndResultAfterShot(Field field) {
        for (Ship ship: ships) {
            ShotResult result = shipService.takeShot(ship, field);
            if (ShotResult.HIT == result || ShotResult.SUNK == result) {
                return new AffectedShipAndResult(ship, result);
            }
        }
        return new AffectedShipAndResult(null, ShotResult.MISSED);
    }

    private void insertShipWithPosition(ShipPosition position) {
        ships.add(new Ship(position));
    }

    private boolean allSunk(List<Stamp> stamps) {
        return ships.stream().allMatch(shipService::isSunk);
    }
}
