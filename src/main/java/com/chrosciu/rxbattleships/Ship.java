package com.chrosciu.rxbattleships;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Ship {
    public final int x;
    public final int y;
    public final int size;
    public final boolean horizontal;
}
