package com.romaingoguet.android.blood.utils;

import android.util.Log;

import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.data.models.News;
import com.romaingoguet.android.blood.data.models.Result;


import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final String EFS_ARTICLE_TYPE = "Article EFS: ";
    public static final String EFS_FACEBOOK_TYPE = "Facebook";
    public static final String EFS_TWITTER_TYPE = "Compte Twitter";
    public static final String EFS_YOUTUBE_TYPE = "Youtube";

    public static final String CLIC_HERE = "Voir détails";
    public static final String DATE_SEPARATOR = "/";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_TIMELINE = "dd MMMM";

    public static final String[] MONTH_ABBREVIATION = {
            "JAN",
            "FEV",
            "MARS",
            "AVR",
            "MAI",
            "JUIN",
            "JUIL",
            "AOUT",
            "SEP",
            "OCT",
            "NOV",
            "DEC"
    };

    /**
     * @param dat
     * @return
     */
    public static Timestamp convertTime(String dat) {
        Timestamp timestamp = new java.sql.Timestamp(0);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date parsedDate = dateFormat.parse(dat);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            Log.d("hour", "convertTime: " + e);
        }
        return timestamp;
    }

    /**
     * Convert String date to Ts
     *
     * @param date
     * @param hour
     * @return Timestamp
     */
    public static Timestamp convertStringDatetoTS(String date, String hour) {
        Timestamp timestamp = new java.sql.Timestamp(0);
        try {
            String[] dateArray = date.split(" ");
            String formatDate = dateArray[1] + "-" + getMonthNumber(dateArray[2].toLowerCase()) + "-2019";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh'h'mm");
            String dat = formatDate + "-" + hour;
            Date parsedDate = dateFormat.parse(dat);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            Log.d("hour", "convertTime: " + e);
        }
        return timestamp;
    }

    /**
     * @param markup
     * @return cleaned text from html
     */
    public static String clean(String markup) {
        String markup2 = markup.replaceAll("<br />\\s+<br />\\s", "\n");
        String markup3 = markup2.replaceAll("<br />", "");
        return markup3.trim();
    }

    /**
     * @param date
     * @return calendar date
     */
    public static String calendarDateToString(Calendar date) {
        String dateString;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.FRANCE);
        dateString = format.format(date.getTime());
        return dateString;
    }

    /**
     * @param dateString
     * @return
     */
    public static Calendar stringDateToCalendar(String dateString) {
        Calendar cal = Calendar.getInstance();
        String[] bla = dateString.split(DATE_SEPARATOR);
        cal.set(Integer.valueOf(bla[2]), Integer.valueOf(bla[1]), Integer.valueOf(bla[0]));
        return cal;
    }

    /**
     * @param timestamp
     * @return string date
     */
    public static String tsDateToString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT, Locale.FRANCE);
        return df2.format(date);
    }

    /**
     * @param timestamp
     * @return String date
     */
    public static String tsDatetoTimelineString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT_TIMELINE, Locale.FRANCE);
        return df2.format(date);
    }

    /**
     * @param string
     * @return timestamp date
     */
    public static long stringDateToTs(String string) {
        Calendar cal = Calendar.getInstance();
        String[] bla = string.split(DATE_SEPARATOR);
        cal.set(Integer.valueOf(bla[2]), Integer.valueOf(bla[1]) - 1, Integer.valueOf(bla[0]), 12, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Add the year to the don list to display on the Dons view in the recyclerView
     *
     * @param list
     * @return list with the year incorporated
     */
    public static List<Don> addYearToList(List<Don> list) {
        DateTime dateTime;
        DateTime dateTime2;
        dateTime = new DateTime(list.get(0).getDate());
        list.add(0, new Don(0, String.valueOf(dateTime.getYear()), ""));
        int i = 1;
        while (i < list.size()) {
            dateTime = new DateTime(list.get(i).getDate());
            dateTime2 = new DateTime(list.get(i - 1).getDate());
            if (dateTime.getYear() != dateTime2.getYear() && list.get(i).getDate() != 0 && list.get(i - 1).getDate() != 0) {
                list.add(i, new Don(0, String.valueOf(dateTime.getYear()), ""));
            }
            i++;
        }
        return list;
    }

    /**
     * @param url
     * @return string to display on the slider description
     */
    public static String getNewsType(String url) {
        String type = "";
        if (url.contains("dondesang.efs.sante.fr")) {
            type = EFS_ARTICLE_TYPE + cleanEfsUrlFromNews(url);
        } else if (url.contains("facebook.com")) {
            type = EFS_FACEBOOK_TYPE;
        } else if (url.contains("twitter")) {
            type = EFS_TWITTER_TYPE;
        } else if (url.contains("youtube")) {
            type = EFS_YOUTUBE_TYPE;
        }
        return type;
    }

    /**
     * tranform slugish url to string readable by human
     *
     * @param url
     * @return readable string from the slug
     */
    private static String cleanEfsUrlFromNews(String url) {
        String cleanedurl = url.replace("https://dondesang.efs.sante.fr/", "");
        return cleanedurl.replace("-", " ");
    }

    /**
     * @param monthName
     * @return month in this format "10" or "03"
     */
    private static String getMonthNumber(String monthName) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MMMM", Locale.FRANCE).parse(monthName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int number = cal.get(Calendar.MONTH) + 1;
        if (number < 10) {
            return ("0" + String.valueOf(number));
        } else {
            return String.valueOf(number);
        }
    }

    /**
     * Clean news
     *
     * @param news
     * @return
     */
    public static List<News> cleanNews(List<News> news) {
        for (News aNews :
                news) {
            if (aNews.link.contains("appli")) {
                news.remove(aNews);
            }
        }
        return news;
    }

    /**
     * @param don
     * @return String
     */
    public static String[] getStringDate(Don don) {
        long ts = don.getDate();
        Date date = new Date(ts);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String[] model = {Integer.toString(day), getStringMonth(month), Integer.toString(year)};
        return model;
    }

    /**
     * @param don
     * @return
     */
    public static String[] getStringDate(Result don) {
        long ts = 0;
        if (!don.getDate().equals(CLIC_HERE)) {
            ts = stringDateToTs(don.getDate());
        }
        Date date = new Date(ts);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String[] model = {Integer.toString(day), getStringMonth(month), Integer.toString(year)};
        return model;
    }


    /**
     * @param month
     * @return
     */
    public static String getStringMonth(int month) {
        return MONTH_ABBREVIATION[month];
    }

}
