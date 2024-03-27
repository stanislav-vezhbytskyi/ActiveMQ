package shpp.app;

import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {
    private static final int INDEX_A_LETTER_IN_ASCII = 65;
    private final Random rand;

    public DataGenerator() {
        rand = new Random();
    }

    public String generateName(int minLength, int maxLength) {
        int length = rand.nextInt(maxLength - minLength + 1) + minLength;

        return rand.ints(65, 123)
                .filter(num -> (num > 96 || num < 91))
                .limit(length)
                .mapToObj(c -> (char) c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
    }

    public int generateCount() {
        return rand.nextInt(1000);
    }

    public LocalDate generateDate() {
        int year = rand.nextInt(36) + 1;
        int month = rand.nextInt(12) + 1;
        //todo: change this. Month can have more than 28 days
        int dayOfMonth = rand.nextInt(28) + 1;

        return LocalDate.of(year, month, dayOfMonth);
    }
}
