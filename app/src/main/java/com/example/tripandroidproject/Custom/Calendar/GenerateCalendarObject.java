package com.example.tripandroidproject.Custom.Calendar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class GenerateCalendarObject {
    public static Calendar generateCalendar(String date, String time) {

        List<String> dateSp = Arrays.asList(date.split("-"));
        List<String> timeSp = Arrays.asList(time.split("-"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSp.get(0)));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateSp.get(1)));
        calendar.set(Calendar.YEAR, Integer.parseInt(dateSp.get(2)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSp.get(0)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeSp.get(1)));
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }
    public static String generateStringDate(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.YEAR));
    }
    public static String generateStringTme(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "-" + String.valueOf(calendar.get(Calendar.MINUTE));
    }
}
