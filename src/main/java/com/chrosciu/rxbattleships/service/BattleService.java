package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.ShotWithResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BattleService {
    Mono<Void> getBattleReadyMono();
    Flux<ShotWithResult> getShotResultFlux();
}
