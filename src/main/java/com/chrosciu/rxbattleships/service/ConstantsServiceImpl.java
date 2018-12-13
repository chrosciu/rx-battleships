package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class ConstantsServiceImpl implements ConstantsService {
    @Override
    public int[] getShipSizes() {
        return Constants.SHIP_SIZES;
    }
}
