package org.poo.cb;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {
    final private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
    //X is the offset (-05:00) from 00:00:00-05:00

    public static ZonedDateTime parseDate(String dateString) {
        // Extract the offset part from the string
        String offsetPart = dateString.substring(19);
        // Remove the colon from the offset
        String offset = offsetPart.substring(0, 3) + offsetPart.substring(4);

        // Replace the offset part in the original string with the modified offset
        String modifiedDateString = dateString.substring(0, 19) + offset;
        return ZonedDateTime.parse(modifiedDateString,formatter);
    }

}
