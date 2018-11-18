package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Shot;
import reactor.core.publisher.Flux;

public interface ShotFluxService {
    Flux<Shot> getShotFlux();
}
