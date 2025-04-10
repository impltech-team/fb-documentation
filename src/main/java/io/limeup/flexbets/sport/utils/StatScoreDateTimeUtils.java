package io.limeup.flexbets.sport.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatScoreDateTimeUtils {

    public static String formatDateTime(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

}
