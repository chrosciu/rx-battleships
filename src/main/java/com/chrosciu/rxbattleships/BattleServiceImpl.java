package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ShipFluxService shipFluxService;
    private final BoardMouseAdapter boardMouseAdapter;

    private boolean[][] ships = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    private boolean[][] shots = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    @Getter
    private boolean finished = false;

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
        Flux<Ship> shipFlux = shipFluxService.getShipFlux().doOnNext(new Consumer<Ship>() {
            @Override
            public void accept(Ship ship) {
                for (int i = 0; i < ship.size; ++i) {
                    if (ship.horizontal) {
                        ships[ship.x+i][ship.y] = true;
                    } else {
                        ships[ship.x][ship.y+i] = true;
                    }
                }
            }
        });
        battleReadyMono = shipFlux.then();
        shotFlux = boardMouseAdapter.getShotFlux().doOnNext(shot -> {
            if (!shots[shot.x][shot.y]) {
                shots[shot.x][shot.y] = true;
                checkFinished();
            }
        });
    }

    private void checkFinished() {
        for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
            for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                if (ships[x][y] && !shots[x][y]) {
                    return;
                }
            }
        }
        finished = true;
    }
}
