package com.chrosciu.rxbattleships;

import reactor.core.publisher.Flux;

public interface ShipFluxService {
    Flux<Ship> getShipFlux();
}
