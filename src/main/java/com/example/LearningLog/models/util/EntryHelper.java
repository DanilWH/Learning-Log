package com.example.LearningLog.models.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EntryHelper {
    /**
     * @return A formatted string of an LocalDateTime instance.
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, Y HH:mm");

        return dateFormat.format(localDateTime);
    }
}
