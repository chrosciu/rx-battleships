package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.model.Field;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class FieldFluxServiceImpl implements FieldFluxService {
    private final BoardMouseAdapter boardMouseAdapter;

    @Getter
    private Flux<Field> fieldFlux;
    private FluxSink<Field> fieldFluxSink;

    @PostConstruct
    private void init() {
        fieldFlux = Flux.create(sink -> fieldFluxSink = sink);
        boardMouseAdapter.registerFieldListener(shot -> fieldFluxSink.next(shot));
    }

}
