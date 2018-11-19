package com.chrosciu.rxbattleships.model;

import lombok.Builder;

@Builder
/**
 * Shot representation on board
 * NOTE: (0,0) coordinates represents the top left cell on board
 */
public class Shot {
    /**
     * Horizontal coordinate of shot
     */
    public final int x;
    /**
     * Vertical coordinate of shot
     */
    public final int y;
}
