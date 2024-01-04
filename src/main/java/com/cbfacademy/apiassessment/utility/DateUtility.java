package com.cbfacademy.apiassessment.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class DateUtility {
    // gets the date today
    public String getCurrentFormattedDate() {
        LocalDateTime today = LocalDateTime.now();
        String formattedDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return formattedDate;
    }

    // puts the date into date/time (YYYYMMDDT0000) format for alphavantage api url
    public String getAlphaVantageTimeDateFormat() {
        String date = getCurrentFormattedDate();
        String dateFormat = date.replace("-", "");
        String dateTimeFormat = dateFormat + "T0000";
        return dateTimeFormat;

    }

    // styles the date pretty for output
    public String getDateTimePretty(String date) {

        // TODO the timeszone of AlphaVantage is UTC, so the articles show different -
        // time to the api response json

        // split given date into parts
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        String hour = date.substring(9, 11);
        String minute = date.substring(11, 13);
        String second = date.substring(13, 15);

        // create ISO_LOCAL_DATE_TIME string format for date
        String dateTimeString = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;

        // convert string into a LocalDate instance
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString);

        // style the date and time (specific format)
        DateTimeFormatter localizedFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

        String formattedDateTime = parsedDateTime.format(localizedFormatter);

        return formattedDateTime;
    }
}
