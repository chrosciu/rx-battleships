package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.gui.ShotListener;
import com.chrosciu.rxbattleships.model.Shot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ShotFluxServiceImpl implements ShotFluxService {
    private final BoardMouseAdapter boardMouseAdapter;

    @Getter
    private Flux<Shot> shotFlux;

    private FluxSink<Shot> shotFluxSink;

    @PostConstruct
    private void init() {
        shotFlux = Flux.create(sink -> shotFluxSink = sink);
        boardMouseAdapter.registerShotListener(shot -> shotFluxSink.next(shot));
    }

}
