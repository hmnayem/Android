package com.game.string.blooddonararchive;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private DateHelper() {

    }

    public static String getDateString(long dateValue) {

        Date date = new Date(dateValue);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dateString = "";

        dateString += calendar.get(Calendar.HOUR) + ":";
        dateString += calendar.get(Calendar.MINUTE);

        int now = calendar.get(Calendar.AM_PM);

        if (now == Calendar.AM) {
            dateString += "AM ";
        } else {
            dateString += "PM ";
        }

        dateString += getMonth(Calendar.MONTH) + " ";
        dateString += calendar.get(Calendar.DAY_OF_MONTH);

        return dateString;

    }

    private static String getMonth(int month) {

        switch (month) {
            case 0:
                return "Jan";

            case 1:
                return "Feb";

            case 2:
                return "Mar";

            case 3:
                return "Apr";

            case 4:
                return "May";

            case 5:
                return "Jun";

            case 6:
                return "Jul";

            case 7:
                return "Aug";

            case 8:
                return "Sep";

            case 9:
                return "Oct";

            case 10:
                return "Nov";

            case 11:
                return "Dec";
        }

        return null;
    }
}
