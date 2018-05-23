package com.thesis.smile.utils.iota;

import org.threeten.bp.LocalDateTime;

public class Utils {
    public static int createNewID(){
        LocalDateTime now = LocalDateTime.now();
        return Integer.parseInt(now.format(org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}
