package com.chrosciu.rxbattleships;

import java.util.Arrays;

public class Utils {
    public static void copy(boolean[][] src, boolean[][] dst) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[i].length; ++j) {
                dst[i][j] = src[i][j];
            }
        }
    }
}
