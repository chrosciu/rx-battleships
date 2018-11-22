package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Stamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service representing battle logic exposed to GUI
 */
public interface BattleService {
    /**
     * Get Mono object which sends completion signal only - when all ships are placed on board
     * @return Mono described above
     */
    Mono<Void> getShipsReadyMono();

    /**
     * Get Flux object which send Stamp signals after any shot, and completion one when all ships are sunk
     * If after shot a ship is sunk Stamp objects for all ship fields (with SUNK status) should be signalled
     * @return Flux described above
     */
    Flux<Stamp> getStampFlux();
}
