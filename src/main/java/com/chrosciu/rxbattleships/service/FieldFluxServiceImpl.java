package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class FieldFluxServiceImpl implements FieldFluxService {
    private final BoardMouseAdapter boardMouseAdapter;

    private Flux<Field> fieldFlux;

    public Flux<Field> getFieldFlux() {
        if (null == fieldFlux) {
            fieldFlux = createFieldFlux();
        }
        return fieldFlux;
    }

    private Flux<Field> createFieldFlux() {
        return Flux.create(sink -> boardMouseAdapter.registerFieldListener(sink::next));
    }
}
