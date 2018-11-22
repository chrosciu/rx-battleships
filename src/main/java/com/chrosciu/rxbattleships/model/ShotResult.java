package com.chrosciu.rxbattleships.model;

/**
 * Enum representing shot result
 */
public enum ShotResult {
    /**
     * No ship has been hit by shot
     */
    MISSED,
    /**
     * Ship has been hit by shot but not sunk yet
     */
    HIT,
    /**
     * Ship has been hit by shot and sunk
     */
    SUNK
}
