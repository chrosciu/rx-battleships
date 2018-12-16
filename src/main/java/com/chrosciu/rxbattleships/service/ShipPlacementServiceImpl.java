package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.config.Constants;
import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.ShipPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipPlacementServiceImpl implements ShipPlacementService {
    private final RandomService randomService;

    private boolean[][] ships = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    @Override
    public ShipPosition placeShip(int size) {
        while (true) {
            int x;
            int y;
            boolean horizontal = randomService.booleanRandom();
            int a = (int) ((Constants.BOARD_SIZE + 1 - size) * randomService.random()) % (Constants.BOARD_SIZE + 1 - size);
            int b = (int) (Constants.BOARD_SIZE * randomService.random()) % Constants.BOARD_SIZE;
            if (horizontal) {
                x = a;
                y = b;
            } else {
                x = b;
                y = a;
            }
            if (!isCollision(horizontal, x, y, size)) {
                for (int i = 0; i < size; ++i) {
                    if (horizontal) {
                        ships[x+i][y] = true;
                    } else {
                        ships[x][y+i] = true;
                    }
                }
                return new ShipPosition(new Field(x,y), size, horizontal);
            }
            /* Artificial timeout to increase cost of method call */
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isCollision(boolean horizontal, int x, int y, int size) {
        for (int i = 0; i < size; ++i) {
            int a = horizontal ? x + i : x;
            int b = horizontal ? y : y + i;
            if (isCollision(a, b)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollision(int x, int y) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int a = x + i;
                int b = y + j;
                if (a >= 0 && a < Constants.BOARD_SIZE && b >= 0 && b < Constants.BOARD_SIZE && ships[a][b]) {
                    return true;
                }
            }
        }
        return false;
    }
}
