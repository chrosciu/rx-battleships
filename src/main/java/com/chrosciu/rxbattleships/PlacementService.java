package com.chrosciu.rxbattleships;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PlacementService {
    public Mono<Void> getPlacement() {
        return Mono.empty();
    }
}
