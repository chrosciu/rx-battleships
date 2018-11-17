package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Ship;
import reactor.core.publisher.Flux;

public interface ShipFluxService {
    Flux<Ship> getShipFlux();
}
