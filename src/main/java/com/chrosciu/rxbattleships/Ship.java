package com.chrosciu.rxbattleships;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Ship {
    final int x;
    final int y;
    final int size;
    final boolean horizontal;
}
