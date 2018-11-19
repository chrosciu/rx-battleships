package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.model.Ship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ShipFluxServiceImpl implements ShipFluxService {
    private final ShipPlacementService shipPlacementService;

    @Override
    public Flux<Ship> getShipFlux() {
        return Flux.error(new NotImplementedException());
    }
}
