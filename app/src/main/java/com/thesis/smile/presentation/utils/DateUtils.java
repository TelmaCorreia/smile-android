package com.thesis.smile.presentation.utils;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


public class DateUtils {

    public static LocalDateTime stringToLocalDateTime(String date, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return  LocalDateTime.parse(date, formatter);
    }
}
