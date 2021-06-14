package nl.manthos.idbewijzen.util;

import nl.manthos.idbewijzen.config.IDBSettingsConfig;

import java.util.GregorianCalendar;
import java.util.Random;

public class DataGenerator {

    private IDBSettingsConfig settingsConfig;

    public static int leeftijd() {
        int min = 18;
        int max = 90;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }

    public static String generateLengte(final Random random,
                                        final double lowerBound,
                                        final double upperBound,
                                        final int decimalPlaces){

        if(lowerBound < 0 || upperBound <= lowerBound || decimalPlaces < 0){
            throw new IllegalArgumentException("[IDBewijzen - Error (Lengte)]");
        }

        final double dbl =
                ((random == null ? new Random() : random).nextDouble() //
                        * (upperBound - lowerBound))
                        + lowerBound;
        return String.format("%." + decimalPlaces + "f", dbl);
    }

    public static String lengte() {
        final Random rnd = new Random();
        int decpl = 2;
        double low = 1.40;
        double high = 2.10;
        return generateLengte(rnd, low, high, decpl);
    }

    public static String birthday() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1960, 2007);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.get(gc.DAY_OF_MONTH) + "-" + (gc.get(gc.MONTH) + 1) + "-" + (gc.get(gc.YEAR));
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
