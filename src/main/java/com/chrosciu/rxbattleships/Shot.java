package com.chrosciu.rxbattleships;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
public class Shot {
    public final int x;
    public final int y;
}
