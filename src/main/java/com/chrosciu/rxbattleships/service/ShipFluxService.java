package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Ship;
import reactor.core.publisher.Flux;

/**
 * Service wrapping ships placement service logic to reactive stream
 */
public interface ShipFluxService {
    /**
     * Get Flux object that signals each Ship object placed on board
     * and completed when all ships are ready
     * @return Flux described above
     */
    Flux<Ship> getShipFlux();
}
