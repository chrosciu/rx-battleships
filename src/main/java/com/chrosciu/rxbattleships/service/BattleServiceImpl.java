package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.model.Stamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ShipPositionFluxService shipPositionFluxService;
    private final FieldFluxService fieldFluxService;
    private final ShipService shipService;

    @Override
    public Mono<Void> getShipsReadyMono() {
        return Mono.error(new NotImplementedException());
    }

    @Override
    public Flux<Stamp> getStampFlux() {
        return Flux.error(new NotImplementedException());
    }
}
