package com.chrosciu.rxbattleships.model;

import lombok.NonNull;

public class ShotWithResult extends Shot {
    @NonNull public final ShotResult shotResult;

    private ShotWithResult(Shot shot, ShotResult shotResult) {
        super(shot.x, shot.y);
        this.shotResult = shotResult;
    }

    public static ShotWithResult of(Shot shot, ShotResult shotResult) {
        return new ShotWithResult(shot, shotResult);
    }
}
