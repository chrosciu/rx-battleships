package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.model.ShotWithResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ShipFluxService shipFluxService;
    private final ShotFluxService shotFluxService;

    @Override
    public Mono<Void> getShipsReadyMono() {
        return Mono.error(new NotImplementedException());
    }

    @Override
    public Flux<ShotWithResult> getShotResultFlux() {
        return Flux.error(new NotImplementedException());
    }
}
