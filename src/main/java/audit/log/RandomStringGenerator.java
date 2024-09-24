package audit.log;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomStringGenerator {

    private final Builder builder;
    private final SecureRandom random;

    public static final String LETTER_UPPER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER_LOWER_POOL = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGIT_POOL = "123456789";
    public static final int CHINESE_START = 0x4e00;
    public static final int CHINESE_END = 0x9fa5;

    private RandomStringGenerator(Builder builder) {
        this.builder = builder;
        this.random = new SecureRandom();
    }

    private String generate() {
        List<Character> characters = new ArrayList<>();
        StringBuilder pool = new StringBuilder();
        for (int i = 0; i < this.builder.atLeastUpperLetter; i++) {
            characters.add(LETTER_UPPER_POOL.charAt(random.nextInt(LETTER_UPPER_POOL.length())));
            pool.append(LETTER_UPPER_POOL);
        }
        for (int i = 0; i < this.builder.atLeastLowerLetter; i++) {
            characters.add(LETTER_LOWER_POOL.charAt(random.nextInt(LETTER_LOWER_POOL.length())));
            pool.append(LETTER_LOWER_POOL);
        }
        for (int i = 0; i < this.builder.atLeastLetter - characters.size(); i++) {
            characters.add(pool.charAt(random.nextInt(pool.length())));
        }
        for (int i = 0; i < this.builder.atLeastDigit; i++) {
            characters.add(DIGIT_POOL.charAt(random.nextInt(DIGIT_POOL.length())));
            pool.append(DIGIT_POOL);
        }
        for (int i = 0; i < this.builder.chinese; i++) {
            characters.add((char) (CHINESE_START + random.nextInt(CHINESE_END - CHINESE_START + 1)));
        }
        int remainder = this.builder.length - characters.size();
        for (int i = 0; i < remainder; i++) {
            characters.add(pool.charAt(random.nextInt(pool.length())));
        }
        Collections.shuffle(characters);
        StringBuilder sbr = new StringBuilder(this.builder.length);
        for (char c : characters) {
            sbr.append(c);
        }

        return sbr.toString();
    }

    static Builder builder() {

        return new Builder();
    }

    public final static class Builder {
        private int length = 0;
        private int atLeastLetter = 0;
        private int atLeastUpperLetter = 0;
        private int atLeastLowerLetter = 0;
        private int atLeastDigit = 0;
        private int chinese = 0;

        private Builder() {
        }

        public Builder length(int i) {
            this.length = i;

            return this;
        }

        public Builder atLeastLetter(int i) {
            this.atLeastLetter = i;

            return this;
        }

        public Builder atLeastUpperLetter(int i) {
            this.atLeastUpperLetter = i;

            return this;
        }

        public Builder atLeastLowerLetter(int i) {
            this.atLeastLowerLetter = i;

            return this;
        }

        public Builder atLeastDigit(int i) {
            this.atLeastDigit = i;

            return this;
        }

        public Builder chinese(int i) {
            this.chinese = i;

            return this;
        }

        public String generate() {

            return new RandomStringGenerator(this).generate();
        }

    }

}
