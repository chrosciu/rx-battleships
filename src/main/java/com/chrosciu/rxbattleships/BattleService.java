package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BattleService {
    private final PlacementService placementService;

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
        battleMono = placementService.getPlacementMono().doOnSuccess(aVoid -> Utils.copy(placementService.getShips(), ships));
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
