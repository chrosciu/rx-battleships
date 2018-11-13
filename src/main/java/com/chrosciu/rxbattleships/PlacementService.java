package com.chrosciu.rxbattleships;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlacementService {

    @Getter
    private boolean[][] ships = new boolean[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    public void placeShips() {
        placeShip(4);
        placeShip(3);
        placeShip(3);
        placeShip(2);
        placeShip(2);
        placeShip(2);
        placeShip(1);
        placeShip(1);
        placeShip(1);
        placeShip(1);
    }

    private void placeShip(int size) {
        while (true) {
            int x;
            int y;
            boolean horizontal = Math.random() < 0.5;
            int a = (int) ((Constants.BOARD_SIZE + 1 - size) * Math.random()) % (Constants.BOARD_SIZE + 1 - size);
            int b = (int) (Constants.BOARD_SIZE * Math.random()) % Constants.BOARD_SIZE;
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
                break;
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
