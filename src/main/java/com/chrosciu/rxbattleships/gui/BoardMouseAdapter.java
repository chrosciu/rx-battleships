package com.chrosciu.rxbattleships.gui;

import com.chrosciu.rxbattleships.model.Shot;
import reactor.core.publisher.Flux;

public interface BoardMouseAdapter extends MouseAdapter {
    void registerShotListener(ShotListener shotListener);
}
