package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                .map(this::getStatusAfterShot)
                .takeUntil(this::allSunk);
    }

    private ShotResult getStatusAfterShot(Shot shot) {
        for (int i = 0; i < shipHits.size(); ++i) {
            CellStatus cellStatus = shipHits.get(i).takeShot(shot);
            if (CellStatus.HIT == cellStatus || CellStatus.SUNK == cellStatus) {
                log.info("{} -> {}", shipHits.get(i), cellStatus);
                return ShotResult.builder().x(shot.x).y(shot.y).cellStatus(cellStatus).build();
            }
        }
        log.info("{}", CellStatus.MISSED);
        return ShotResult.builder().x(shot.x).y(shot.y).cellStatus(CellStatus.MISSED).build();
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
        if (CellStatus.SUNK != shotResult.cellStatus) {
            return false;
        }
        return shipHits.stream().allMatch(ShipHits::isSunk);
    }
}
