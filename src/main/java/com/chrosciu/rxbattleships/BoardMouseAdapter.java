package com.chrosciu.rxbattleships;

import reactor.core.publisher.Flux;

public interface BoardMouseAdapter extends MouseAdapter {
    Flux<Shot> getShotFlux();
}
