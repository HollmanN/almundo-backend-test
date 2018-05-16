package com.almundo.backendtest.util;

import java.util.Random;

/**
 * @author hollman_castro
 */
public class RandomNumber {
    public static int getRandomDuration(int minDuration, int maxDuration){
        int diff = maxDuration - minDuration;
        Random rn = new Random();
        int duration = rn.nextInt(diff + 1);
        return duration + minDuration;
    }
}
