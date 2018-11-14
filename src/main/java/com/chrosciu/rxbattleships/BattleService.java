package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BattleService {
    private final ShipFluxService shipFluxService;

    @Getter
    private boolean[][] ships = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    @Getter
    private boolean[][] shots = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    @Getter
    private boolean finished = false;

    @Getter
    private Mono<Void> battleMono;

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
        battleMono = shipFlux.then();
    }

    public void takeShot(int x, int y) {
        if (!shots[x][y]) {
            shots[x][y] = true;
            checkFinished();
        }
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
