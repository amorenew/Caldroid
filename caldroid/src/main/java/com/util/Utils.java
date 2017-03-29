package com.util;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by TCIG_PC_54 on 1/3/2017.
 */

public class Utils {
    /**
     * convert english number to Arabic number
     *
     * @param number the english number text
     * @return
     */
    public static String getLocalNumber(String number) {
        if (!isRTL()) {
            return number;
        }
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i))) {
                builder.append(arabicChars[(int) (number.charAt(i)) - 48]);
            } else {
                builder.append(number.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * convert english number to Arabic number
     *
     * @param numberText the english number text
     * @return
     */
    public static String getLocalNumber(int numberText) {
        String number = String.valueOf(numberText);
        if (!isRTL())
            return number;
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i))) {
                builder.append(arabicChars[(int) (number.charAt(i)) - 48]);
            } else {
                builder.append(number.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String getLocalNumber(double numberText) {
        String number = String.valueOf(numberText);
        if (!isRTL())
            return number;
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i))) {
                builder.append(arabicChars[(int) (number.charAt(i)) - 48]);
            } else {
                builder.append(number.charAt(i));
            }
        }
        return builder.toString();
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public static long getDayInMillisSecond(int daysCount) {
        int HOURS_IN_DAY = 24;
        int MINUTES_IN_HOUR = 60;
        int SECONDS_IN_MINUTE = 60;
        int MILLIS_IN_SECOND = 1000;
        return (long) daysCount * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
    }

    public static void addDay(Calendar calendar, int daysCount) {
        long newTime = calendar.getTimeInMillis() + getDayInMillisSecond(daysCount);
        calendar.setTimeInMillis(newTime);
    }

    public static long getDayInMillisSecond() {
        return getDayInMillisSecond(1);
    }


}
