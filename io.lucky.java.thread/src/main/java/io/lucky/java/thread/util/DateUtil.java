package io.lucky.java.thread.util;

public class DateUtil {

    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTES = MILLIS_PER_SECOND * 60;

    private DateUtil(){}

    public static long now(){
        return System.currentTimeMillis();
    }
}
