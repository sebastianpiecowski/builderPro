package com.pl.exaco.builder_pro.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DatetimeParser {

    public static String parseToString(Timestamp datetime) {
        String out = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datetime);
        return out;
    }
}
