package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.model.ShipPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ShipPositionFluxServiceImpl implements ShipPositionFluxService {
    private final ShipPlacementService shipPlacementService;
    private final ConstantsService constantsService;

    @Override
    public Flux<ShipPosition> getShipPositionFlux() {
        return Flux.error(new NotImplementedException());
    }
}
