package com.chrosciu.rxbattleships.gui;

import lombok.NonNull;

public interface BoardMouseAdapter extends MouseAdapter {
    /**
     * Register listener to be notified about performed shots
     * @param shotListener - listener to be registered
     */
    void registerShotListener(@NonNull ShotListener shotListener);
}
