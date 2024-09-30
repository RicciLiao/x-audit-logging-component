package ricciliao.audit.log;

import java.util.Random;

public final class RandomGenerator {

    public static RandomStringGenerator.Builder nextString() {

        return RandomStringGenerator.builder();
    }

    public static int randomLength(int min, int max) {

        return new Random().nextInt((max - min) + 1) + min;
    }

}
