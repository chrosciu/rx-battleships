package com.chrosciu.rxbattleships;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BattleService {
    Mono<Void> getBattleReadyMono();
    boolean getShot(int x, int y);
    boolean getShip(int x, int y);
    boolean isFinished();
    Flux<Shot> getShotFlux();
}
