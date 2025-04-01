package io.limeup.flexbets.sport.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatScoreDateTimeUtils {

    public static String formatDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

}
