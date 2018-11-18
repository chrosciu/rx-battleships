package com.chrosciu.rxbattleships.gui;

public interface BoardMouseAdapter extends MouseAdapter {
    void registerShotListener(ShotListener shotListener);
}
