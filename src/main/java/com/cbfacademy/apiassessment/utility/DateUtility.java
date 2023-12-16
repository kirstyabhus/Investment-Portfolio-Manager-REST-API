package com.cbfacademy.apiassessment.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class DateUtility {
    // gets the date 1 week before now
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

    // TODO the timeszone of AlphaVantage is UTC, so the artcles show different time
    // to the api response json
    public String getX(String date) {
        // get YYYYMMDDT0000
        return new String("hello");
    }
}
