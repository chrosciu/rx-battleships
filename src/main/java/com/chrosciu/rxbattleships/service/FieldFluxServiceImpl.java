package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.exception.NotImplementedException;
import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class FieldFluxServiceImpl implements FieldFluxService {
    private final BoardMouseAdapter boardMouseAdapter;

    @Override
    public Flux<Field> getFieldFlux() {
        return Flux.error(new NotImplementedException());
    }
}
