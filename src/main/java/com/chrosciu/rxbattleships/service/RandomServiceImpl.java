package com.chrosciu.rxbattleships.service;

import org.springframework.stereotype.Component;

@Component
public class RandomServiceImpl implements RandomService {
    @Override
    public int intRandom(int range) {
        if (range <= 0) {
            throw new IllegalArgumentException("Range must be positive integer");
        }
        return (int)(Math.random() * range) % range;
    }

    @Override
    public boolean booleanRandom() {
        return Math.random() < 0.5;
    }
}
