package user11681.usersmanual.math;

import java.util.ArrayList;
import java.util.List;

public class RomanNumerals {
    protected static final List<Character> ROMAN = new ArrayList<>();
    protected static final List<Integer> DECIMAL = new ArrayList<>();

    static {
        ROMAN.add('I');
        ROMAN.add('V');
        ROMAN.add('X');
        ROMAN.add('L');
        ROMAN.add('C');
        ROMAN.add('D');
        ROMAN.add('M');

        DECIMAL.add(1);
        DECIMAL.add(5);
        DECIMAL.add(10);
        DECIMAL.add(50);
        DECIMAL.add(100);
        DECIMAL.add(500);
        DECIMAL.add(1000);
    }

    public static String fromDecimal(long decimal) {
        if (decimal == 0) {
            return "nulla";
        }

        final StringBuilder roman = new StringBuilder();
        final int index = DECIMAL.size() - 1;
        final int largest = DECIMAL.get(index);

        while (decimal >= largest) {
            roman.append(ROMAN.get(index));
            decimal -= largest;
        }

        int div = 1;

        while (decimal >= div) {
            div *= 10;
        }

        div /= 10;

        while (decimal > 0) {
            int lastNum = (int) (decimal / div);

            if (lastNum <= 3) {
                for (int i = 0; i < lastNum; i++) {
                    roman.append(toRoman(div));
                }
            } else if (lastNum == 4) {
                roman.append(toRoman(div)).append(toRoman(div * 5));
            } else if (lastNum <= 8) {
                roman.append(toRoman(div * 5));

                for (int i = 0, end = lastNum - 5; i < end; i++) {
                    roman.append(toRoman(div));
                }
            } else if (lastNum == 9) {
                roman.append(toRoman(div)).append(toRoman(div * 10));
            }

            decimal %= div;
            div /= 10;
        }

        return roman.toString();
    }

    public static long toDecimal(final String roman) {
        final char[] characters = roman.toCharArray();
        long decimal = 0;

        for (int i = 0, length = characters.length; i < length; i++) {
            final int current = toDecimal(characters[i]);

            if (i < length - 1 && toDecimal(characters[i + 1]) > current) {
                decimal -= current;
            } else {
                decimal += current;
            }
        }

        return decimal;
    }

    public static int toDecimal(final char character) {
        return DECIMAL.get(ROMAN.indexOf(character));
    }

    public static char toRoman(final int decimal) {
        return ROMAN.get(DECIMAL.indexOf(decimal));
    }
}
