package com.chrosciu.rxbattleships;

import javafx.scene.control.Cell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BattleServiceImpl implements BattleService {
    private final ShipFluxService shipFluxService;
    private final BoardMouseAdapter boardMouseAdapter;

    private boolean[][] shots = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    private List<ShipHits> shipHits = new ArrayList<>();

    @Getter
    private Mono<Void> battleReadyMono;

    @Getter
    private Flux<ShotResult> shotResultFlux;

    @PostConstruct
    private void init() {
        battleReadyMono = shipFluxService.getShipFlux()
                .doOnNext(this::insertShip).then();
        shotResultFlux = boardMouseAdapter.getShotFlux()
                .filter(this::noShotAlreadyHere)
                .doOnNext(this::markShot)
                .flatMap((Function<Shot, Publisher<ShotResult>>) shot -> Flux.fromIterable(getShotResultsAfterShot(shot)))
                .takeUntil(this::allSunk);
    }

    private List<ShotResult> getShotResultsAfterShot(Shot shot) {
        Optional<ShipHits> shipHitsOptional = shipHits.stream().filter(shipHits -> shipHits.isHit(shot)).findFirst();
        if (shipHitsOptional.isPresent()) {
            ShipHits shipHits = shipHitsOptional.get();
            shipHits.takeShot();
            List<Shot> shots = shipHits.getShotsToReport(shot);
            CellStatus cellStatus = shipHits.isSunk() ? CellStatus.SUNK : CellStatus.HIT;
            return shots.stream().map(new Function<Shot, ShotResult>() {
                @Override
                public ShotResult apply(Shot shot) {
                    return ShotResult.builder().x(shot.x).y(shot.y).cellStatus(cellStatus).build();
                }
            }).collect(Collectors.toList());
        } else {
            return Collections.singletonList(ShotResult.builder().x(shot.x).y(shot.y).cellStatus(CellStatus.MISSED).build());
        }
    }

    private void insertShip(Ship ship) {
        shipHits.add(new ShipHits(ship));
    }

    private boolean noShotAlreadyHere(Shot shot) {
        return !shots[shot.x][shot.y];
    }

    private void markShot(Shot shot) {
        shots[shot.x][shot.y] = true;
    }

    private boolean allSunk(ShotResult shotResult) {
        return shipHits.stream().allMatch(ShipHits::isSunk);
    }
}
