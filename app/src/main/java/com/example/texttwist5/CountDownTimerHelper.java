package com.example.texttwist5;

import android.util.Log;

public class CountDownTimerHelper {
    public static long convertStringToLong(String time)
    {
        long conversionTime = 0;
        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }
        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }
        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;
        return conversionTime;
    }
    public static String convertLongToString(long milliTime)
    {
        String hour = String.valueOf(milliTime / (60 * 60 * 1000));
        long getMin = milliTime - (milliTime / (60 * 60 * 1000));
        String min = String.valueOf(getMin / (60 * 1000));
        String second = String.valueOf((getMin % (60 * 1000)) / 1000);

        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (min.length() == 1) {
            min = "0" + min;
        }
        if (second.length() == 1) {
            second = "0" + second;
        }
        return hour + ":" + min + ":" + second;
    }
    public static long convertStringToLongContinueTimer(String time)
    {
        long conversionTime = 0;
        String getHour = time.substring(0, 2);
        String getMin = time.substring(3, 5);
        String getSecond = time.substring(6, 8);

        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }
        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }
        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;
        return conversionTime;
    }
}
