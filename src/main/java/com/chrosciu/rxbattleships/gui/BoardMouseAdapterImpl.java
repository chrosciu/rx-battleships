package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.Shot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.awt.event.MouseEvent;

@Component
@RequiredArgsConstructor
public class BoardMouseAdapterImpl implements BoardMouseAdapter {

    @Getter
    private Flux<Shot> shotFlux;

    private FluxSink<Shot> shotFluxSink;

    @PostConstruct
    private void init() {
        shotFlux = Flux.create(sink -> shotFluxSink = sink);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / Constants.CELL_SIZE - 1;
        int y = e.getY() / Constants.CELL_SIZE - 1;
        if (x >= 0 && x < Constants.BOARD_SIZE && y >= 0 && y < Constants.BOARD_SIZE) {
            Shot shot = Shot.builder().x(x).y(y).build();
            shotFluxSink.next(shot);
        }
    }
}
