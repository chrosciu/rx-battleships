package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.ShipPosition;
import reactor.core.publisher.Flux;

/**
 * Service wrapping ships placement service logic to reactive stream
 */
public interface ShipPositionFluxService {
    /**
     * Get Flux object that signals position of each ship placed on board
     * and completes when all ships are positioned
     * @return Flux described above
     */
    Flux<ShipPosition> getShipPositionFlux();
}
