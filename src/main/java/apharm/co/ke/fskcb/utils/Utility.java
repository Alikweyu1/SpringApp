package apharm.co.ke.fskcb.utils;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class Utility {
    public static String generatePIN() {
        Random rand = new Random();
        int randInt = rand.nextInt(10000);
        String password = String.format("%04d", randInt);
        return password;
    }
}
