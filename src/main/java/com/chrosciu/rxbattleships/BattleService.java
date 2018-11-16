package com.chrosciu.rxbattleships;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BattleService {
    Mono<Void> getBattleReadyMono();
    Flux<ShotResult> getShotResultFlux();
}
