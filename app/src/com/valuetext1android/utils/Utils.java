package com.valuetext1android.utils;

import com.valuetext1android.Model.SmsMsgBucket;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JANI SHAIK on 28/12/2019
 */

public class Utils {

    /**
     *
     * @param value
     * @return
     */
    public static String checkString(String value) {
        String str;
        if (value == null || value.equals("") || value.equals("null")
                || value.trim().length() == 0) {
            str = "";
        } else
            str = value;
        return str;
    }

    /**
     * Check string is not null or empty
     * @param value
     * @return
     */
    public static Boolean isStringNotNullOrEmpty(String value) {
        if (value != null && !value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Convert GMT string to Date
     * @param dateString
     * @return
     */
    public static Date convertStringToDate(String dateString) {
        if (isStringNotNullOrEmpty(dateString)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            try {
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get Current Time in GMT
     * @return
     */
    public static String getCurrentTimeInGMT() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

}

