package com.egs.bankservice.web.helper;

import com.egs.bankservice.util.ObjectMapperUtils;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonMethodHelper {
    public final static String MOCK_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdG0iLCJleHAiOjE2NzYwMjk0MTMsInJvbCI6WyJhZG1pbiJdfQ.g89JTs8sJjTUMmsUbwUdVvuR0aWk9V0rwIyhLYFEzb7SDhftHAmqL4V-yR4U4D5uYW-LAbbFo655A8h_KSgzMQ";

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
//        return ObjectMapperUtils.map(obj, String.class);
    }

    public static Date generateDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = formatter.parse(dateStr);
        return date;
    }

    public static String generateRandomAccountNumber() throws ParseException {
        long d = (long) (Math.random() * 1000000000L);
        return Long.toString(d);
    }

    public static String generateRandomCardNumber() throws ParseException {
        long d = (long) (Math.random() * 10000000000000000L);
        return Long.toString(d);
    }

}
