package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.model.Shot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ShotFluxServiceImpl implements ShotFluxService {
    private final BoardMouseAdapter boardMouseAdapter;

    @Override
    public Flux<Shot> getShotFlux() {
        return Flux.error(new NotImplementedException());
    }
}
