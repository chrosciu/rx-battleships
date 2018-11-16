package com.chrosciu.rxbattleships;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
public class ShotResult {
    public final int x;
    public final int y;
    @NonNull public final FieldStatus fieldStatus;
}
