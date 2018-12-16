package com.chrosciu.rxbattleships.service;

import org.springframework.stereotype.Component;

@Component
public class RandomServiceImpl implements RandomService {
    @Override
    public double random() {
        return Math.random();
    }

    @Override
    public boolean booleanRandom() {
        return Math.random() < 0.5;
    }
}
