package com.carcoop.helpme.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    private static String TAG = "TimeUtils";

    public static String getTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }

    public static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

//    public static String convTxttoTime(String datestring){
//
//        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = new Date(datestring);
//        return  dateFormat.format(date);
//    }

    public static String getCurrentDate() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Log.e(TAG, "getCurrentDate: Date-> " + date + " calendar => " + calendar);
        return dateFormat.format(date);
    }


    public static String convTxttoTime(String datestring) {
        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        Date date = null;
        Log.e(TAG, "convTxttoTime: time and date -> " + date + " pattern--> " + dt.toPattern());
        try {
            Log.e(TAG, "convTxttoTime: not going");
            date = dt.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // *** same for the format String below
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
//        return date;
        return dt1.format(date);
    }

    public static Date convTxttoTime(String datestring, int i) {
        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dt.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // *** same for the format String below
//        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        return date;
//        return  dt1.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String covertTimeToText(String date) {
        String convTime = null;
        String prefix = "";
        String suffix = "Ago";
        Date nowTime = new Date();
        Date pasTime = new Date(date);
        long dateDiff = nowTime.getTime() - pasTime.getTime();
        long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
        long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
        long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
        long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

        if (second < 60) {
//            convTime = currentTime(date);
            convTime = "Few" + " Seconds " + suffix;
        } else if (minute < 60) {
//            convTime =currentTime(date);
            convTime = minute + " Minutes " + suffix;
        } else if (hour < 24) {
//            convTime =currentTime(date);
            convTime = hour + " Hours " + suffix;
        } else if (day >= 7) {
            if (day > 360) {
                convTime = (day / 360) + " Years " + suffix;
            } else if (day > 30) {
                convTime = (day / 30) + " Months " + suffix;
            } else {
                convTime = (day / 7) + " Week " + suffix;
            }
        } else if (day < 7) {
            convTime = day + " Days " + suffix;
        }
        return convTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String currentTime(String data) {
        android.icu.text.DateFormat dateFormat = new android.icu.text.SimpleDateFormat("hh:mm aa");
        String dateString = dateFormat.format(new Date(data)).toString();
        return dateString;
    }

    public static String getCreatedAtDate(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dt.format(date);
    }

}
