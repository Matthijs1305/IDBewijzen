package nl.manthos.idbewijzen.util;

import java.util.GregorianCalendar;

public class DataGenerator {

    public static int leeftijd() {
        int min = 18;
        int max = 90;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }

    public static double lengte() {
        double min = 1.40;
        double max = 2.10;
        double random_double = (double) Math.floor(Math.random()*(max-min+1)+min);
        return random_double;
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
