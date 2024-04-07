package shpp.app;

import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {
    private static final int ASCII_UPPERCASE_A = 65;
    private static final int ASCII_UPPERCASE_Z = 90;
    private static final int ASCII_LOWERCASE_A = 97;
    private static final int ASCII_LOWERCASE_Z = 122;
    private final Random rand;

    public DataGenerator() {
        rand = new Random();
    }

    public String generateName(int minLength, int maxLength) {
        int length = rand.nextInt(maxLength - minLength + 1) + minLength;

        return rand.ints(ASCII_UPPERCASE_A, ASCII_LOWERCASE_Z + 1)
                .filter(num -> (num <= ASCII_UPPERCASE_Z || num >= ASCII_LOWERCASE_A))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public int generateEDDR(){
        return rand.nextInt(100000000);
    }

    public int generateCount() {
        return rand.nextInt(1000);
    }

    public LocalDate generateDate() {
        int year = rand.nextInt(36) + 1;
        int month = rand.nextInt(12) + 1;
        int dayOfMonth = rand.nextInt(28) + 1; // У реальності деякі місяці мають більше днів

        return LocalDate.of(year, month, dayOfMonth);
    }
}
