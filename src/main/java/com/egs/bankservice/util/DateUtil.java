package com.egs.bankservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date generateDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = formatter.parse(dateStr);
        return date;
    }

}
