package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class BattleServiceImpl implements BattleService {
    private final ShipFluxService shipFluxService;
    private final BoardMouseAdapter boardMouseAdapter;

    private boolean[][] shots = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    private List<ShipWithHits> shipsWithHits = new ArrayList<>();

    @Getter
    private Mono<Void> battleReadyMono;

    @Getter
    private Flux<ShotWithResult> shotResultFlux;

    @PostConstruct
    private void init() {
        battleReadyMono = shipFluxService.getShipFlux()
                .doOnNext(this::insertShip).then();
        shotResultFlux = boardMouseAdapter.getShotFlux()
                .filter(this::noShotAlreadyHere)
                .doOnNext(this::markShot)
                .flatMap((Function<Shot, Publisher<ShotWithResult>>) shot -> Flux.fromIterable(getShotResultsAfterShot(shot)))
                .takeUntil(this::allSunk);
    }

    private List<ShotWithResult> getShotResultsAfterShot(Shot shot) {
        Optional<ShipWithHits> shipHitsOptional = shipsWithHits.stream().filter(shipWithHits -> shipWithHits.isHit(shot)).findFirst();
        return shipHitsOptional.map(shipWithHits -> shipWithHits.takeShot(shot))
                .orElse(Collections.singletonList(ShotWithResult.of(shot, ShotResult.MISSED)));
    }

    private void insertShip(Ship ship) {
        shipsWithHits.add(new ShipWithHits(ship));
    }

    private boolean noShotAlreadyHere(Shot shot) {
        return !shots[shot.x][shot.y];
    }

    private void markShot(Shot shot) {
        shots[shot.x][shot.y] = true;
    }

    private boolean allSunk(ShotWithResult shotWithResult) {
        return shipsWithHits.stream().allMatch(ShipWithHits::isSunk);
    }
}
