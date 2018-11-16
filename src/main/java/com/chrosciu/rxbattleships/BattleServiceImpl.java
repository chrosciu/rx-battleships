package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ShipFluxService shipFluxService;
    private final BoardMouseListener boardMouseListener;

    private boolean[][] ships = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    private boolean[][] shots = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    @Getter
    private Mono<Void> battleReadyMono;

    @Getter
    private Flux<Shot> shotFlux;

    @Override
    public boolean getShot(int x, int y) {
        return shots[x][y];
    }

    @Override
    public boolean getShip(int x, int y) {
        return ships[x][y];
    }

    @PostConstruct
    private void init() {
        Flux<Ship> shipFlux = shipFluxService.getShipFlux()
                .doOnNext(this::insertShip);
        battleReadyMono = shipFlux.then();
        shotFlux = boardMouseListener.getShotFlux()
                .filter(this::noShotAlready)
                .takeUntil(this::isFinished);
    }

    private void insertShip(Ship ship) {
        for (int i = 0; i < ship.size; ++i) {
            if (ship.horizontal) {
                ships[ship.x+i][ship.y] = true;
            } else {
                ships[ship.x][ship.y+i] = true;
            }
        }
    }

    private boolean noShotAlready(Shot shot) {
        return !shots[shot.x][shot.y];
    }

    private boolean isFinished(Shot shot) {
        shots[shot.x][shot.y] = true;
        for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
            for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                if (ships[x][y] && !shots[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
}
