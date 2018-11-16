package com.chrosciu.rxbattleships;

import reactor.core.publisher.Flux;

import java.awt.event.MouseListener;

public interface BoardMouseListener extends MouseListener {
    Flux<Shot> getShotFlux();
}
